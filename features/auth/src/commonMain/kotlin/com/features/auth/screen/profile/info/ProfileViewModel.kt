package com.features.auth.screen.profile.info

import com.core.common.extension.onFinally
import com.core.data.remote.dto.UserDto
import com.core.data.repository.api.AuthRepository
import com.core.presentation.base.BaseViewModel
import com.features.auth.mapper.toUi
import com.navigation.NavRoute
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel (
    private val authRepository: AuthRepository
) : BaseViewModel<ProfileState, ProfileAction, ProfileEffect>(initialState = ProfileState()) {

    override fun handleAction(action: ProfileAction) {
        when (action) {
            ProfileAction.EditProfile -> navigateToForm()
        }
    }

    override fun loadInitialData() {
        launchWithRetry(onRetry = ::loadInitialData, onStart = ::showLoading) {
            authRepository.fetchCurrentProfile()
                .onFailure(action = ::sendError)
                .onFinally(action = ::hideLoading)
                .onSuccess(action = ::updateUser)
        }
    }

    private fun showLoading() {
        updateState { copy(loading = true) }
    }

    private fun hideLoading() {
        updateState { copy(loading = false) }
    }

    private fun updateUser(userDto: UserDto) {
        updateState { copy(user = userDto.toUi()) }
    }

    private fun navigateToForm() {
        postEffect(effect = ProfileEffect.NavigateToForm(route = NavRoute.EditProfile))
    }
}