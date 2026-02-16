package com.features.auth.screen.login

import com.core.common.extension.onFinally
import com.core.data.repository.api.AuthRepository
import com.core.presentation.base.BaseViewModel
import com.features.auth.data.mapper.toDto
import com.navigation.NavRoute
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel (
    private val authRepository: AuthRepository
) : BaseViewModel<LoginState, LoginAction, LoginEffect>(initialState = LoginState()) {

    override fun handleAction(action: LoginAction) {
        when(action) {
            LoginAction.Login -> login()
            LoginAction.OpenRegister -> navigateToRegisterScreen()
        }
    }

    private fun login() {
        launchWithRetry(onRetry = ::login, onStart = ::showLoading) {
            authRepository.login(data = currentState.field.toDto())
                .onFailure(action = ::sendError)
                .onFinally(action = ::hideLoading)
        }
    }

    private fun showLoading() {
        updateState { copy(pageLoading = true) }
    }

    private fun hideLoading() {
        updateState { copy(pageLoading = false) }
    }

    private fun navigateToRegisterScreen() {
        postEffect(effect = LoginEffect.NavigateToRegister(route = NavRoute.Register))
    }
}