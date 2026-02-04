package com.features.auth.data.mapper

import com.core.data.remote.dto.LoginRequestDto
import com.features.auth.data.model.LoginField

fun LoginField.toDto() = LoginRequestDto(
    username = username.text.toString(),
    password = password.text.toString()
)