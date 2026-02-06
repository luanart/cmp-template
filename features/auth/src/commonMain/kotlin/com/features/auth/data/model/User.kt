package com.features.auth.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val name: String = "",
    val email: String = "",
    val image: String? = null,
) {

    companion object {
        val Dummy = User(name = "Unknown", email = "error@unknown.com")
    }
}