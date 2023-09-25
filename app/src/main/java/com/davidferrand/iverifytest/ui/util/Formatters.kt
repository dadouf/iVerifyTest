package com.davidferrand.iverifytest.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatNicely(): String {
    // Stretch goal: format like "2 weeks ago"
    val format = SimpleDateFormat("dd/MM/yy hh:mma", Locale.US)
    return format.format(this)
}