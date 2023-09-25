package com.davidferrand.iverifytest.ui.device

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davidferrand.iverifytest.model.Device
import com.davidferrand.iverifytest.ui.ErrorIndicator
import com.davidferrand.iverifytest.ui.theme.IVerifyTestTheme

@Composable
fun DevicesScreen(
    modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = viewModel(),
) {
    val uiState by viewModel.uiStateObservable.subscribeAsState(initial = DeviceUiState.DEFAULT)

    DevicesScreen(
        uiState = uiState,
        modifier = modifier,
        onListFooterClick = { viewModel.loadMore() },
        onRetryClick = { viewModel.retryLoading() },
        onFilterChanged = { viewModel.filterDevices(it) }
    )
}

@Composable
fun DevicesScreen(
    uiState: DeviceUiState,
    modifier: Modifier = Modifier,
    onListFooterClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    onFilterChanged: (String?) -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Column {
            DeviceList(
                displayedDevices = uiState.displayedDevices,
                totalDevicesLoaded = uiState.totalDevicesLoaded,
                isLoading = uiState.isLoading,
                canLoadMore = uiState.canLoadMore,
                onFooterButtonClick = onListFooterClick,
            )
        }

        if (uiState.error != null) {
            ErrorIndicator(
                error = uiState.error,
                onRetryClick = onRetryClick,
            )
        }

        var isFiltering by remember { mutableStateOf(false) }
        FilterButton(
            isFiltering = isFiltering,
            filter = uiState.filter,
            onClick = {
                isFiltering = !isFiltering
                if (!isFiltering) {
                    onFilterChanged(null)
                }
            },
            onFilterChanged = onFilterChanged,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DevicesScreenPreview_Success() {
    IVerifyTestTheme {
        DevicesScreen(
            uiState = DeviceUiState.DEFAULT.copy(
                displayedDevices = listOf(Device.DUMMY),
                isLoading = false,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DevicesScreenPreview_Loading() {
    IVerifyTestTheme {
        DevicesScreen(
            uiState = DeviceUiState.DEFAULT.copy(
                isLoading = true,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DevicesScreenPreview_Error() {
    IVerifyTestTheme {
        DevicesScreen(
            uiState = DeviceUiState.DEFAULT.copy(
                error = Throwable(),
                isLoading = false,
            )
        )
    }
}