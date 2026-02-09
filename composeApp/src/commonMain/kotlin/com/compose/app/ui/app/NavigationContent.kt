package com.compose.app.ui.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.compose.app.buildNavigation
import com.compose.app.enums.NavItem
import com.compose.app.enums.NavItem.Companion.activeItem
import com.compose.app.ui.NavScaffold
import com.compose.app.util.NavTransitions
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.util.ScaffoldState
import com.core.presentation.util.rememberScreenType
import com.navigation.LocalNavigator
import com.navigation.NavRoute
import com.navigation.navigateAsTopNav
import kotlinx.collections.immutable.toPersistentList

@Suppress("ParamsComparedByRef")
@Composable
fun NavigationContent(navController: NavHostController) {
    val navItems = remember { NavItem.entries.toPersistentList() }
    val screenType = rememberScreenType()
    val scaffoldState = rememberSaveable(saver = ScaffoldState.Saver) { ScaffoldState() }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val selectedNavItem by remember(backStackEntry) {
        derivedStateOf { navItems.activeItem(backStackEntry) }
    }

    CompositionLocalProvider(
        LocalNavigator provides navController,
        LocalScaffoldState provides scaffoldState
    ) {
        NavScaffold(
            state = scaffoldState,
            navItems = navItems,
            selected = selectedNavItem,
            screenType = screenType,
            onNavigate = {
                navController.navigateAsTopNav(it.route)
            },
            primaryActionContent = {
                FloatingActionButton(
                    onClick = {
                        navController.navigateAsTopNav(NavRoute.Test)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            },
            content = {
                NavHost(
                    navController = navController,
                    startDestination = NavRoute.Splash,
                    enterTransition = { NavTransitions.enter() },
                    exitTransition = { NavTransitions.exit() },
                    popEnterTransition = { NavTransitions.popEnter() },
                    popExitTransition = { NavTransitions.popExit() },
                    builder = { buildNavigation() }
                )
            }
        )
    }
}