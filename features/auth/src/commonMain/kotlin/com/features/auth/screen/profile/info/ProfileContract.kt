package com.features.auth.screen.profile.info

import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState
import com.features.auth.model.User
import com.navigation.NavRoute

@Immutable
data class ProfileState(val user: User = User(), val loading: Boolean = false): ViewState

sealed interface ProfileAction: ViewAction {
    data object EditProfile: ProfileAction
}

sealed interface ProfileEffect: ViewEffect {
    data class NavigateToForm(val route: NavRoute): ProfileEffect
}