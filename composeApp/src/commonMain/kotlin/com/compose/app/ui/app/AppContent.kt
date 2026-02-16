package com.compose.app.ui.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.compose.app.buildNavigation
import com.compose.app.ui.NavScaffold
import com.compose.app.ui.navigation.NavItem
import com.compose.app.util.NavTransitions
import com.navigation.NavRoute
import com.navigation.navigateAsTopNav
import kotlinx.collections.immutable.PersistentList

@Suppress("ParamsComparedByRef")
@Composable
fun AppContent(
    navItems: PersistentList<NavItem>,
    selected: NavItem?,
    navController: NavHostController
) {
    NavScaffold(
        navItems = navItems,
        selected = selected,
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