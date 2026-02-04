package com.features.auth.mapper

import com.core.data.remote.dto.UserDto
import com.features.auth.model.User

fun UserDto.toUi() = User(
    name = "$firstName $lastName",
    email = email,
    image = image
)