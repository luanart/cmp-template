package com.compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.compose.app.enums.NavItem.Companion.activeItem
import com.compose.app.ui.app.AppAction
import com.compose.app.ui.app.NavEffect
import com.compose.app.ui.app.AppContent
import com.compose.app.ui.app.AppViewModel
import com.compose.app.util.SetStatusBarStyle
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.LaunchedViewEffect
import com.navigation.LocalNavigator
import com.navigation.navigateAsStart
import com.navigation.navigateAsTopNav
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val navController = rememberNavController()
    val viewModel = koinViewModel<AppViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedViewEffect(viewModel.effect) { effect ->
        when(effect) {
            is NavEffect.NavigateToNavItem -> navController.navigateAsTopNav(effect.route)
            is NavEffect.NavigateToStartDest -> navController.navigateAsStart(effect.route)
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect {
            val activeNavItem = state.navItems.activeItem(it)
            if (state.selectedNavItem != activeNavItem) {
                viewModel.handleAction(AppAction.UpdateSelectedAppItem(activeNavItem))
            }
        }
    }

    AppTheme(isDarkMode = state.darkMode) {
        SetStatusBarStyle(isDarkMode = state.darkMode)
        CompositionLocalProvider(value = LocalNavigator provides navController) {
            AppContent(
                navItems = state.navItems,
                selected = state.selectedNavItem,
                navController = navController
            )
        }
    }
}