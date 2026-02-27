package com.features.auth.screen.profile.info

import androidx.compose.runtime.Composable
import com.core.presentation.base.BaseScreen
import com.navigation.LocalNavigator
import com.resources.Res
import com.resources.my_profile
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileScreen() {
    val navigator = LocalNavigator.current

    BaseScreen(
        viewModel = koinViewModel<ProfileViewModel>(),
        pageTitle = stringResource(Res.string.my_profile),
        onLaunchEffect = { effect ->
            when(effect) {
                is ProfileEffect.NavigateToForm -> navigator.navigate(effect.route)
            }
        },
    ) { state, dispatcher ->
        ProfileContent(state, dispatcher)
    }
}