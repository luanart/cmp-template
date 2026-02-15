package com.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

val LocalNavigator = staticCompositionLocalOf<NavController> {
    error("CompositionLocal LocalNavController not present")
}

val LocalNavBackStackEntry = compositionLocalOf<NavBackStackEntry?> { null }