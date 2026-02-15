package com.features.home.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.base.BaseScreen
import com.resources.Res
import com.resources.home
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val error by viewModel.appError.collectAsStateWithLifecycle()


    BaseScreen(
        error = error,
        pageTitle = stringResource(Res.string.home),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.AddHome,
                    contentDescription = null
                )
            }
        }
    ) {
        HomeContent(state = state, dispatcher = viewModel::handleAction)
    }
}