package com.features.auth.data.mapper

import com.core.data.remote.dto.UserDto
import com.features.auth.data.model.User

fun UserDto.toUi() = User(
    name = "$firstName $lastName",
    email = email,
    image = image
)