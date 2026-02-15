package com.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.core.presentation.component.CustomSnackbarHost
import com.core.presentation.component.CustomSnackbarHostState
import com.core.presentation.component.LocalSnackbarHostState
import com.navigation.LocalNavigator

@Composable
fun AppScaffold(
    navigation: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val navigator = LocalNavigator.current
    val snackbarState = remember { CustomSnackbarHostState() }

    val stackEntry by navigator.currentBackStackEntryAsState()
    val stackEntryId = stackEntry?.id.orEmpty()

    val configs = remember { mutableStateMapOf<String, ScaffoldConfig>() }
    val currentConfig = configs[stackEntryId] ?: ScaffoldConfig()

    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbarState,
        LocalScaffoldController provides ScaffoldController(
            setConfig = { key, config -> configs[key] = config },
            removeConfig = { key -> configs.remove(key) }
        )
    ) {
        Scaffold(
            bottomBar = navigation,
            topBar = {
                currentConfig.topBar?.invoke()
            },
            floatingActionButton = {
                currentConfig.fab?.invoke()
            },
            snackbarHost = {
                CustomSnackbarHost(state = snackbarState, modifier = Modifier.imePadding())
            },
            content = content
        )
    }
}