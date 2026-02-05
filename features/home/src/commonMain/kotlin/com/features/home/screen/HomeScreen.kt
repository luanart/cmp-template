package com.features.home.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.base.BaseScreen
import com.core.presentation.util.LaunchedViewEffect
import com.navigation.LocalNavigator
import com.resources.Res
import com.resources.home
import com.resources.my_profile
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {
    val navigator = LocalNavigator.current
    val viewModel = koinViewModel<HomeViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val error by viewModel.appError.collectAsStateWithLifecycle()

    LaunchedViewEffect(viewModel.effect) { effect ->
        when(effect) {
            is HomeEffect.NavigateToTestPage -> {
                navigator.navigate(effect.route)
            }
        }
    }

    BaseScreen(
        error = error,
        pageTitle = stringResource(Res.string.home),
        floatingButton = {
            FloatingActionButton(onClick = { viewModel.handleAction(action = HomeAction.OpenTestPage) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.my_profile)
                )
            }
        }
    ) {
        HomeContent(state = state, dispatcher = viewModel::handleAction)
    }
}