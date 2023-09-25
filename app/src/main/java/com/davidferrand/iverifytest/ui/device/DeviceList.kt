package com.davidferrand.iverifytest.ui.device

import FooterRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidferrand.iverifytest.model.Device
import com.davidferrand.iverifytest.ui.theme.DefaultSpacing
import com.davidferrand.iverifytest.ui.theme.IVerifyTestTheme

@Composable
fun DeviceList(
    displayedDevices: List<Device>,
    totalDevicesLoaded: Int,
    isLoading: Boolean,
    canLoadMore: Boolean,
    modifier: Modifier = Modifier,
    onFooterButtonClick: () -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = DefaultSpacing,
            bottom = 92.dp, // more space at the bottom so we can display the filter
            start = DefaultSpacing,
            end = DefaultSpacing,
        ),
        verticalArrangement = Arrangement.spacedBy(DefaultSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        items(displayedDevices, key = { it.id }, contentType = { "device" }) {
            DeviceRow(it)
        }

        // Stretch goal: use Jetpack Compose Paging library instead
        item(contentType = "footer") {
            FooterRow(
                totalDevicesDisplayed = displayedDevices.size,
                totalDevicesLoaded = totalDevicesLoaded,
                isLoading = isLoading,
                canLoadMore = canLoadMore,
                onFooterButtonClick = onFooterButtonClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceListPreview_Data() {
    IVerifyTestTheme {
        DeviceList(
            displayedDevices = List(5) { Device.DUMMY },
            totalDevicesLoaded = 5,
            isLoading = false,
            canLoadMore = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceListPreview_Loading() {
    IVerifyTestTheme {
        DeviceList(
            displayedDevices = emptyList(),
            totalDevicesLoaded = 0,
            isLoading = true,
            canLoadMore = true,
        )
    }
}
