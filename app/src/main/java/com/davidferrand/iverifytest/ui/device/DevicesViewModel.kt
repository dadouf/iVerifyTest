package com.davidferrand.iverifytest.ui.device

import android.util.Log
import androidx.lifecycle.ViewModel
import com.davidferrand.iverifytest.model.Device
import com.davidferrand.iverifytest.model.DeviceFilter
import com.davidferrand.iverifytest.model.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.Optional
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val repo: DeviceRepository
) : ViewModel() {
    private var disposable: Disposable? = null

    // It's crucial to set a default value
    private val filterSubject = BehaviorSubject.createDefault(Optional.empty<String>())

    val uiStateObservable = Observable
        .combineLatest(
            repo.devicesObservable,
            repo.errorObservable,
            repo.isLoadingObservable,
            repo.canLoadMoreObservable,
            filterSubject
        ) { devices, error, loading, canLoadMore, filterOpt ->
            val filter = filterOpt.getOrNull()
            DeviceUiState(
                isLoading = loading,
                displayedDevices = DeviceFilter.apply(devices, filter),
                totalDevicesLoaded = devices.size,
                error = error.getOrNull(),
                canLoadMore = canLoadMore,
                filter = filter,
            )
        }

    init {
        loadMore() // load some initial data
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun loadMore() {
        Log.d(DevicesViewModel::class.java.simpleName, "loadMore triggered")
        disposable = repo.loadMore()
    }

    fun retryLoading() {
        loadMore()
    }

    fun filterDevices(filter: String?) {
        filterSubject.onNext(Optional.ofNullable(filter))
    }
}

data class DeviceUiState(
    val isLoading: Boolean,
    val error: Throwable?,
    val displayedDevices: List<Device>,
    val totalDevicesLoaded: Int,
    val canLoadMore: Boolean,
    val filter: String?,
) {
    companion object {
        val DEFAULT = DeviceUiState(
            isLoading = true,
            error = null,
            displayedDevices = emptyList(),
            totalDevicesLoaded = 0,
            canLoadMore = true,
            filter = null,
        )
    }
}