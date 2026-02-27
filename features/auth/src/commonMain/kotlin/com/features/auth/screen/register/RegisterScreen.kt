package com.features.auth.screen.register

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.core.presentation.base.BaseScreen

@Composable
internal fun RegisterScreen() {
    BaseScreen(pageTitle = "Register") {
        Text(text = "REGISTER")
    }
}