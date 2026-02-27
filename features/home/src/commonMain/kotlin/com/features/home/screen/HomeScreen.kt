package com.features.home.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.core.presentation.base.BaseScreen
import com.resources.Res
import com.resources.home
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {
    BaseScreen(
        viewModel = koinViewModel<HomeViewModel>(),
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
        },
    ) { state, dispatcher ->
        HomeContent(state, dispatcher)
    }
}