package com.features.auth.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.base.BaseScreen
import com.core.presentation.util.LaunchedViewEffect
import com.navigation.LocalNavigator
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginScreen() {
    val navigator = LocalNavigator.current
    val viewModel = koinViewModel<LoginViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val error by viewModel.appError.collectAsStateWithLifecycle()

    LaunchedViewEffect(viewModel.effect) { effect ->
        when(effect) {
            is LoginEffect.NavigateToRegister -> {
                navigator.navigate(effect.route)
            }
        }
    }

    BaseScreen(error = error, showTopBar = false, showLoading = state.loading) {
        LoginContent(state = state, dispatcher = viewModel::handleAction)
    }
}