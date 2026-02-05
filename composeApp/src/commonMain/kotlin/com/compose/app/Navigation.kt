package com.compose.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.core.presentation.base.BaseScreen
import com.features.auth.route.authRoute
import com.features.auth.route.profileRoute
import com.features.home.route.homeRoute
import com.navigation.NavRoute

fun NavGraphBuilder.buildNavigation() {
    composable<NavRoute.Splash>(
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    composable<NavRoute.Test> {
        BaseScreen(confirmOnBack = true) {
            Text("TEST SCREEN")
        }
    }

    authRoute()
    homeRoute()
    profileRoute()
}