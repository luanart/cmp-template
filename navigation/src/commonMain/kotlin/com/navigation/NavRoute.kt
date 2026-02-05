package com.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

val LocalNavigator = staticCompositionLocalOf<NavController> {
    error("CompositionLocal LocalNavController not present")
}

sealed interface NavRoute {
    @Serializable data object Splash : NavRoute

    // Auth
    @Serializable data object Login : NavRoute
    @Serializable data object Register : NavRoute

    // Main
    @Serializable data object Home : NavRoute
    @Serializable data object Profile : NavRoute
    @Serializable data object EditProfile : NavRoute
    @Serializable data object Test : NavRoute
}

fun startDestination(userId: Int?) = if (userId == null) NavRoute.Login else NavRoute.Home