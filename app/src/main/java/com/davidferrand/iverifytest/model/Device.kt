package com.davidferrand.iverifytest.model

import java.util.Date

data class Device(
    val id: Long,
    val name: String,
    val code: String,
    val latestScanDate: Date?,
) {
    companion object {
        val DUMMY = Device(
            id = 0,
            name = "Dummy device",
            code = "1A2B3C",
            latestScanDate = Date()
        )
    }
}