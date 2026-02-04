package com.core.data.remote.api

import com.core.common.isDebuggingMode

object ApiConfig {

    val baseUrl = when(isDebuggingMode) {
        true -> "https://dummyjson.com/"
        false -> "https://api.real.com/"
    }

    internal object Url {
        const val REFRESH_TOKEN = "auth/refresh"
        const val LOGIN = "auth/login"
        const val CURRENT = "auth/me"
    }
}