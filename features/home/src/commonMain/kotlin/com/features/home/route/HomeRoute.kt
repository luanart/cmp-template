package com.features.home.route

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.features.home.screen.HomeScreen
import com.navigation.NavRoute

fun NavGraphBuilder.homeRoute() {
    composable<NavRoute.Home>(
        enterTransition = { fadeIn(animationSpec = tween(300)) }
    ) {
        HomeScreen()
    }
}