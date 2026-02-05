package com.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptionsBuilder
import kotlin.reflect.KClass

fun NavController.navigateIfResumed(
    destination: NavRoute,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}
) {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(route = destination, builder = navOptionsBuilder)
    }
}

fun NavController.navigateAsTopNav(route: NavRoute) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateAsStart(route: NavRoute) {
    navigate(route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.canGoBack() = previousBackStackEntry != null

fun NavBackStackEntry.isRouteInHierarchy(route: KClass<*>): Boolean {
    return this.destination.hierarchy.any { it.hasRoute(route) }
}