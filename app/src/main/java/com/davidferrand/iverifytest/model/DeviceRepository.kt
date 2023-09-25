package com.davidferrand.iverifytest.model

import android.util.Log
import com.davidferrand.iverifytest.auth.AuthProvider
import com.davidferrand.iverifytest.network.DeviceService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.Optional
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val service: DeviceService,
    private val authProvider: AuthProvider,
) {
    companion object {
        private const val PAGE_SIZE = 100
    }

    private var nextPage = 1 // by experience, pages are 1-indexed

    /*
     * We maintain a simple RAM cache of the loaded devices.
     * Notes for the BehaviorSubjects:
     * - it's crucial to set a default value so that we can construct a UI state at all times
     * - RxJava doesn't support null items so we use Optional<> instead
     */
    private val devicesSubject = BehaviorSubject.createDefault(emptyList<Device>())
    private val errorSubject = BehaviorSubject.createDefault(Optional.empty<Throwable>())
    private val isLoadingSubject = BehaviorSubject.createDefault(false)
    private val canLoadMoreSubject = BehaviorSubject.createDefault(true)

    /* Expose subjects publicly as non-mutable */
    val devicesObservable: Observable<List<Device>> get() = devicesSubject
    val errorObservable: Observable<Optional<Throwable>> get() = errorSubject
    val isLoadingObservable: Observable<Boolean> get() = isLoadingSubject
    val canLoadMoreObservable: Observable<Boolean> get() = canLoadMoreSubject

    fun loadMore(): Disposable {
        val token = authProvider.ensureToken()

        return service.getDevices(auth = "Bearer $token", page = nextPage, pageSize = PAGE_SIZE)
            .doOnSubscribe {
                isLoadingSubject.onNext(true)
                errorSubject.onNext(Optional.empty()) // clear errors
            }
            .doFinally { isLoadingSubject.onNext(false) }
            .subscribe(
                { newDevices ->
                    Log.d(
                        DeviceRepository::class.java.simpleName,
                        "loadMore succeeded: page $nextPage"
                    )

                    val previousDevices = devicesSubject.value ?: emptyList()

                    errorSubject.onNext(Optional.empty())
                    devicesSubject.onNext(previousDevices + newDevices.devices)
                    canLoadMoreSubject.onNext(nextPage <= newDevices.totalPages)
                    nextPage++
                },
                { error ->
                    Log.d(DeviceRepository::class.java.simpleName, "loadMore failed", error)

                    errorSubject.onNext(Optional.of(error))
                    // Do not increment the page, we'll have to retry
                }
            )
    }
}
