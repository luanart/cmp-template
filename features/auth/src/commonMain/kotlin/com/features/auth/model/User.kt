package com.features.auth.model

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val name: String = "Unknown",
    val email: String = "unknown@error.com",
    val image: String? = null,
)