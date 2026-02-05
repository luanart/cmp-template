package com.features.auth.screen.login

import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState
import com.features.auth.data.model.LoginField
import com.navigation.NavRoute

@Immutable
data class LoginState(val field: LoginField = LoginField(), val loading: Boolean = false): ViewState

sealed interface LoginAction: ViewAction {
    data object Login: LoginAction
    data object OpenRegister: LoginAction
}

sealed interface LoginEffect: ViewEffect {
    data class NavigateToRegister(val route: NavRoute): LoginEffect
}