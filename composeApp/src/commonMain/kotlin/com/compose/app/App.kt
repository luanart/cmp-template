package com.compose.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.compose.app.enums.NavItem.Companion.activeItem
import com.compose.app.ui.NavScaffold
import com.compose.app.util.SetStatusBarStyle
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.LaunchedViewEffect
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.util.ScaffoldState
import com.core.presentation.util.rememberScreenType
import com.navigation.LocalNavigator
import com.navigation.NavRoute
import com.navigation.navigateAsStart
import com.navigation.navigateAsTopNav
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel = koinViewModel<AppViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val screenType = rememberScreenType()
    val navController = rememberNavController()
    val scaffoldState = rememberSaveable(saver = ScaffoldState.Saver) { ScaffoldState() }

    LaunchedViewEffect(viewModel.effect) { effect ->
        when(effect) {
            is AppEffect.NavigateToNavItem -> navController.navigateAsTopNav(effect.route)
            is AppEffect.NavigateToStartDest -> navController.navigateAsStart(effect.route)
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect {
            val activeNavItem = state.navItems.activeItem(it)
            if (state.selectedNavItem != activeNavItem) {
                viewModel.handleAction(AppAction.UpdateSelectedNavItem(activeNavItem))
            }
        }
    }

    AppTheme(isDarkMode = state.darkMode) {
        SetStatusBarStyle(isDarkMode = state.darkMode)
        CompositionLocalProvider(
            LocalNavigator provides navController,
            LocalScaffoldState provides scaffoldState
        ) {
            NavScaffold(
                state = scaffoldState,
                navItems = state.navItems,
                selected = state.selectedNavItem,
                screenType = screenType,
                onNavigate = {
                    viewModel.handleAction(AppAction.NavigateTo(it.route))
                },
                primaryActionContent = {
                    FloatingActionButton(
                        onClick = {
                            viewModel.handleAction(AppAction.NavigateTo(NavRoute.Test))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                },
            ) {
                NavHost(
                    navController = navController,
                    startDestination = NavRoute.Splash,
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
                    builder = { buildNavigation() }
                )
            }
        }
    }
}