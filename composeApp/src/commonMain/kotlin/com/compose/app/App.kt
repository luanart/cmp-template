package com.compose.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.app.enums.NavItem
import com.compose.app.enums.NavItem.Companion.activeItem
import com.compose.app.ui.NavScaffold
import com.compose.app.util.SetStatusBarStyle
import com.core.data.local.LocalStorage
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.util.ScaffoldState
import com.core.presentation.util.rememberScreenType
import com.navigation.LocalNavigator
import com.navigation.NavRoute
import com.navigation.navigateAsTopNav
import com.navigation.startDestination
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun App() {
    val local = koinInject<LocalStorage>()
    val userId by local.userId.collectAsStateWithLifecycle(initialValue = 0)
    val darkMode by local.darkMode.collectAsStateWithLifecycle(initialValue = false)

    val navItems = remember { NavItem.entries }
    val screenType = rememberScreenType()
    val navController = rememberNavController()
    val scaffoldState = rememberSaveable(saver = ScaffoldState.Saver) { ScaffoldState() }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentNavigationItem by remember(currentBackStackEntry) {
        derivedStateOf { navItems.activeItem(currentBackStackEntry) }
    }

    LaunchedEffect(userId) {
        if (currentBackStackEntry?.destination?.hasRoute<NavRoute.Splash>() == true) {
            delay(300)
        }

        navController.navigate(route = startDestination(userId)) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    AppTheme(isDarkMode = darkMode) {
        SetStatusBarStyle(isDarkMode = darkMode)
        CompositionLocalProvider(
            LocalNavigator provides navController,
            LocalScaffoldState provides scaffoldState,
        ) {
            Surface {
                NavScaffold(
                    state = scaffoldState,
                    navItems = navItems.toPersistentList(),
                    selected = currentNavigationItem,
                    screenType = screenType,
                    onNavigate = { navController.navigateAsTopNav(destination = it) },
                    primaryActionContent = {
                        FloatingActionButton(
                            onClick = { navController.navigate(route = NavRoute.Test) }
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
}