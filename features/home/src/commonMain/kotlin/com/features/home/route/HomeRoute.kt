package com.features.home.route

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavGraphBuilder
import com.core.presentation.extension.screen
import com.features.home.screen.HomeScreen
import com.navigation.NavRoute

fun NavGraphBuilder.homeRoute(fadeInDuration: Int) {
    screen<NavRoute.Home>(
        enterTransition = { fadeIn(animationSpec = tween(fadeInDuration)) }
    ) {
        HomeScreen()
    }
}