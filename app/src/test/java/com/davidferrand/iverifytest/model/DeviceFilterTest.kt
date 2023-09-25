package com.davidferrand.iverifytest.model

import org.junit.Assert.assertEquals
import org.junit.Test


class DeviceFilterTest {
    private val device1 = Device(id = 1, name = "David", code = "D", latestScanDate = null)
    private val device2 = Device(id = 2, name = "John", code = "J", latestScanDate = null)

    @Test
    fun nullFilterReturnsEverything() {
        val output = DeviceFilter.apply(listOf(device1, device2), null)
        assertEquals(listOf(device1, device2), output)
    }

    @Test
    fun filtersByName() {
        val output = DeviceFilter.apply(listOf(device1, device2), "a")
        assertEquals(listOf(device1), output)
    }

    @Test
    fun filterIsCaseInsensitive() {
        val output1 = DeviceFilter.apply(listOf(device1, device2), "j")
        assertEquals(listOf(device2), output1)

        val output2 = DeviceFilter.apply(listOf(device1, device2), "I")
        assertEquals(listOf(device1), output2)
    }
}