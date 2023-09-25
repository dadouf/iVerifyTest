package com.davidferrand.iverifytest.ui.device

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidferrand.iverifytest.model.Device
import com.davidferrand.iverifytest.ui.theme.IVerifyTestTheme
import com.davidferrand.iverifytest.ui.theme.RoundedCornersSize
import com.davidferrand.iverifytest.ui.theme.SmallSpacing
import com.davidferrand.iverifytest.ui.util.formatNicely


@Composable
fun DeviceRow(
    device: Device,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SmallSpacing),
        modifier = modifier,
    ) {
        Icon(Icons.Default.Face, contentDescription = "User name")

        Column {
            Text(device.name, style = MaterialTheme.typography.bodyLarge)

            Text(
                "Scanned: ${device.latestScanDate?.formatNicely() ?: "never"}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.alpha(0.6f)
            )
        }
        Spacer(Modifier.weight(1f))

        Surface(
            shape = RoundedCornerShape(RoundedCornersSize),
            color = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
        ) {
            Text(
                device.code,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 2.dp),
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DeviceRowPreview() {
    IVerifyTestTheme {
        DeviceRow(Device.DUMMY)
    }
}