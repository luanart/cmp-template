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
    val user: User = User(),
    val loading: Boolean = false,
    val isDarkTheme: Boolean = false,
): ViewState

sealed interface ProfileAction: ViewAction {
    data object EditProfile: ProfileAction
    data class MenuClicked(val menu: ProfileMenu, val checked: Boolean = false): ProfileAction
}

sealed interface ProfileEffect: ViewEffect {
    data class NavigateToForm(val route: NavRoute): ProfileEffect
}