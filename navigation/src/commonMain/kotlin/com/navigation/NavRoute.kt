package com.navigation

import kotlinx.serialization.Serializable

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

fun startDestination(userId: Int?): NavRoute = if (userId == null) NavRoute.Login else NavRoute.Home