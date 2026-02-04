package com.features.auth.mapper

import com.core.data.remote.dto.LoginRequestDto
import com.features.auth.model.LoginField

fun LoginField.toDto() = LoginRequestDto(
    username = username.text.toString(),
    password = password.text.toString()
)