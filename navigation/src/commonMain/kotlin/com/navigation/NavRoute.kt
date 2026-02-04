package com.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

val LocalNavigator = staticCompositionLocalOf<NavController> {
    error("CompositionLocal LocalNavController not present")
}

sealed interface NavRoute {
    @Serializable
    data object Splash : NavRoute

    @Serializable
    data object Login : NavRoute

    @Serializable
    data object Register : NavRoute

    @Serializable
    data object Home : NavRoute

    @Serializable
    data object Profile : NavRoute

    @Serializable
    data object EditProfile : NavRoute

    @Serializable
    data object Test : NavRoute
}