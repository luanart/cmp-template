package com.features.auth.screen.profile.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.base.BaseScreen
import com.core.presentation.util.LaunchedViewEffect
import com.navigation.LocalNavigator
import com.resources.Res
import com.resources.my_profile
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileScreen() {
    val navigator = LocalNavigator.current
    val viewModel = koinViewModel<ProfileViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val error by viewModel.appError.collectAsStateWithLifecycle()

    LaunchedViewEffect(viewModel.effect) { effect ->
        when(effect) {
            is ProfileEffect.NavigateToForm -> {
                navigator.navigate(effect.route)
            }
        }
    }

    BaseScreen(
        error = error,
        pageTitle = stringResource(Res.string.my_profile)
    ) {
        ProfileContent(state = state, dispatcher = viewModel::handleAction)
    }
}