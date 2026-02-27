package com.features.auth.screen.login

import androidx.compose.runtime.Composable
import com.core.presentation.base.BaseScreen
import com.navigation.LocalNavigator
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginScreen() {
    val navigator = LocalNavigator.current

    BaseScreen(
        viewModel = koinViewModel<LoginViewModel>(),
        onLaunchEffect = { effect ->
            when(effect) {
                is LoginEffect.NavigateToRegister -> navigator.navigate(effect.route)
            }
        },
    ) { state, dispatcher ->
        LoginContent(state, dispatcher)
    }
}