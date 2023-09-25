import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidferrand.iverifytest.ui.theme.DefaultSpacing
import com.davidferrand.iverifytest.ui.theme.IVerifyTestTheme
import com.davidferrand.iverifytest.ui.theme.SmallSpacing

@Composable
fun FooterRow(
    totalDevicesDisplayed: Int,
    totalDevicesLoaded: Int,
    isLoading: Boolean,
    canLoadMore: Boolean,
    modifier: Modifier = Modifier,
    onFooterButtonClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = DefaultSpacing)
    ) {
        Spacer(modifier = Modifier.height(SmallSpacing))

        Text(
            if (totalDevicesDisplayed < totalDevicesLoaded) {
                "Found $totalDevicesDisplayed out of $totalDevicesLoaded loaded devices"
            } else {
                "$totalDevicesDisplayed loaded devices"
            },
            textAlign = TextAlign.Center
        )
        if (totalDevicesDisplayed < totalDevicesLoaded && canLoadMore) {
            Text(
                "You may find additional devices after loading",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.alpha(0.6f)
            )
        }

        Spacer(modifier = Modifier.height(DefaultSpacing))

        if (canLoadMore) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(DefaultSpacing)
            ) {
                if (isLoading) {
                    // Placeholder so we can keep centering the button when loading
                    Spacer(modifier = Modifier.size(16.dp))
                }

                Button(onClick = onFooterButtonClick, enabled = !isLoading) {
                    if (isLoading) {
                        Text(text = "Loading...")
                    } else {
                        Text(text = "Load more")
                    }
                }

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                }
            }
        }
    }
}

@Preview
@Composable
fun FooterRowPreview_NoFilter() {
    IVerifyTestTheme {
        FooterRow(
            totalDevicesDisplayed = 1,
            totalDevicesLoaded = 1,
            isLoading = false,
            canLoadMore = true,
        )
    }
}

@Preview
@Composable
fun FooterRowPreview_Filtered() {
    IVerifyTestTheme {
        FooterRow(
            totalDevicesDisplayed = 1,
            totalDevicesLoaded = 2,
            isLoading = false,
            canLoadMore = true,
        )
    }
}

@Preview
@Composable
fun FooterRowPreview_FilteredToZero() {
    IVerifyTestTheme {
        FooterRow(
            totalDevicesDisplayed = 0,
            totalDevicesLoaded = 2,
            isLoading = false,
            canLoadMore = true,
        )
    }
}