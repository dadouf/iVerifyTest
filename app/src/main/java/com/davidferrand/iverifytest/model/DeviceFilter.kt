package com.davidferrand.iverifytest.model

object DeviceFilter {
    fun apply(devices: List<Device>, filter: String?): List<Device> {
        if (filter == null) {
            return devices
        }

        val filterLowercase = filter.lowercase()
        return devices.filter {
            filterLowercase in it.name.lowercase()
        }
    }
}