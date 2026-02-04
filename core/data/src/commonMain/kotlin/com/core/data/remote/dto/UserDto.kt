package com.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String?,
)