package com.features.auth.screen.profile.info

import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState
import com.features.auth.data.model.User
import com.features.auth.enums.ProfileMenu
import com.navigation.NavRoute

@Immutable
data class ProfileState(
    val user: User = User.Dummy,
    val loading: Boolean = false,
    val language: String = "en-US",
    val isDarkTheme: Boolean = false,
    val confirmLogout: Boolean = false
): ViewState

sealed interface ProfileAction: ViewAction {
    data object EditProfile: ProfileAction
    data object Logout: ProfileAction
    data class MenuClicked(val menu: ProfileMenu, val checked: Boolean = false): ProfileAction
    data class ToggleConfirmLogout(val confirm: Boolean): ProfileAction
}

sealed interface ProfileEffect: ViewEffect {
    data class NavigateToForm(val route: NavRoute): ProfileEffect
}