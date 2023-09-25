package com.davidferrand.iverifytest.network

import com.davidferrand.iverifytest.model.Device
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DeviceService {
    /**
     * @param pageSize number of rows to return per page. Default: 100 devices per page. Max: 1,000 devices per page.
     */
    @GET("devices")
    fun getDevices(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Single<GetDevicesResponse>
}

data class GetDevicesResponse(
    val devices: List<Device>,
    val totalPages: Int,
)