package com.features.auth.data.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable

@Immutable
data class LoginField(
    val username: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
)