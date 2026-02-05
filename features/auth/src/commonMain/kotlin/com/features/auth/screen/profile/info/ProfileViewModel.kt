package com.features.auth.screen.profile.info

import androidx.lifecycle.viewModelScope
import com.core.common.extension.onFinally
import com.core.data.local.LocalStorage
import com.core.data.local.SecureStorage
import com.core.data.local.SecureStorage.Companion.clearToken
import com.core.data.remote.dto.UserDto
import com.core.data.repository.api.AuthRepository
import com.core.presentation.base.BaseViewModel
import com.features.auth.data.mapper.toUi
import com.features.auth.enums.ProfileMenu
import com.features.auth.enums.ProfileMenuAction
import com.navigation.NavRoute
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel (
    private val localStorage: LocalStorage,
    private val secureStorage: SecureStorage,
    private val authRepository: AuthRepository,
) : BaseViewModel<ProfileState, ProfileAction, ProfileEffect>(
    initialState = ProfileState()
) {

    override fun handleAction(action: ProfileAction) {
        when(action) {
            ProfileAction.EditProfile -> navigateToForm()
            ProfileAction.Logout -> logout()
            is ProfileAction.MenuClicked -> menuClicked(clicked = action)
            is ProfileAction.ToggleConfirmLogout -> toggleConfirmLogout(action.confirm)
        }
    }

    override fun loadInitialData() {
        initializeDarkTheme()
        loadCurrentUser()
    }

    private fun initializeDarkTheme() {
        viewModelScope.launch {
            localStorage.darkMode.collect { updateState { copy(isDarkTheme = it) } }
        }
    }

    private fun loadCurrentUser() {
        launchWithRetry(onRetry = ::loadCurrentUser, onStart = ::showLoading) {
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

    private fun logout() {
        viewModelScope.launch {
            updateState { copy(confirmLogout = false) }
            secureStorage.clearToken()
            localStorage.clearSession()
        }
    }

    private fun menuClicked(clicked: ProfileAction.MenuClicked) {
        viewModelScope.launch {
            when(clicked.menu) {
                ProfileMenu.DARK_MODE -> if (clicked.menu.action == ProfileMenuAction.SWITCH) {
                    localStorage.setThemeAsDarkMode(clicked.checked)
                    updateState { copy(isDarkTheme = clicked.checked) }
                }
                ProfileMenu.CHANGE_LANGUAGE -> {
                    /** action to change language */
                }
                ProfileMenu.HELP_N_SUPPORT -> {
                    /** action to navigate to help & support */
                }
                ProfileMenu.PRIVACY_POLICY -> {
                    /** action to navigate to privacy policy */
                }
            }
        }
    }

    private fun toggleConfirmLogout(confirm: Boolean) {
        updateState { copy(confirmLogout = confirm) }
    }
}