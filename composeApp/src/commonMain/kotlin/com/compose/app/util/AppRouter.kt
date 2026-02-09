package com.compose.app.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.navigation.NavRoute
import com.navigation.navigateAsStart
import kotlinx.coroutines.delay
import kotlin.time.Clock

sealed interface RoutingState {
    data object Loading: RoutingState
    data object Authenticated: RoutingState
    data object Unauthenticated: RoutingState
}

@Suppress("ParamsComparedByRef")
@Composable
fun LaunchAppRouter(
    userId: Int?,
    navController: NavController,
    minimumSplashDurationMs: Long = 0L,
) {
    // ---------------------------------
    // Layout Inspector safety gate
    // ---------------------------------
    var compositionReady by remember { mutableStateOf(false) }

    SideEffect {
        compositionReady = true
    }

    // ---------------------------------
    // Splash timing (optional)
    // ---------------------------------
    val splashStartTime = remember { Clock.System.now().toEpochMilliseconds() }

    // ---------------------------------
    // Routing state
    // ---------------------------------
    val routingState = remember(userId) {
        when (userId) {
            null -> RoutingState.Unauthenticated
            else -> RoutingState.Authenticated
        }
    }

    // ---------------------------------
    // Prevent duplicate navigation
    // ---------------------------------
    var lastRoutedState by rememberSaveable {
        mutableStateOf<RoutingState?>(null)
    }

    // ---------------------------------
    // Navigation side-effect
    // ---------------------------------
    LaunchedEffect(routingState, compositionReady) {
        if (!compositionReady) return@LaunchedEffect
        if (routingState == lastRoutedState) return@LaunchedEffect

        @Suppress("AssignedValueIsNeverRead")
        lastRoutedState = routingState

        // Ensure minimum splash time if needed
        if (minimumSplashDurationMs > 0) {
            val elapsed = Clock.System.now().toEpochMilliseconds() - splashStartTime
            if (elapsed < minimumSplashDurationMs) {
                delay(minimumSplashDurationMs - elapsed)
            }
        }

        when (routingState) {
            RoutingState.Loading -> Unit
            RoutingState.Authenticated -> {
                navController.navigateAsStart(NavRoute.Home)
            }
            RoutingState.Unauthenticated -> {
                navController.navigateAsStart(NavRoute.Login)
            }
        }
    }
}

