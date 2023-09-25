package com.davidferrand.iverifytest.auth

import android.util.Log
import com.davidferrand.iverifytest.prefs.AppPrefs
import javax.inject.Inject

class AuthProvider @Inject constructor(
    private val appPrefs: AppPrefs,
) {
    /** This returns a valid token, either by retrieving the stored one or by logging in. */
    fun ensureToken(): String {
        var token = appPrefs.token

        if (token == null) {
            Log.d(AuthProvider::class.java.simpleName, "No token stored, logging in")
            token = login()
            appPrefs.token = token
        }

        return token
    }

    private fun login(): String {
        // For the sake of this exercise, this is just a basic implementation that returns
        // the hardcoded token directly. In a real case, there would be a series of network calls
        // for token exchange.
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6ImlWZXJpZnkiLCJ1c2VySWQiOjYyLCJleHAiOjE3MjUxMjg5Mjl9.Vzy-WfuNVplsuv9yuSgPQQNivRWmtywM144j4BcScPs"
    }
}