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
import com.compose.app.util.NAV_ANIM_DURATION
import com.core.presentation.base.BaseScreen
import com.core.presentation.extension.screen
import com.features.auth.route.authRoute
import com.features.auth.route.profileRoute
import com.features.home.route.homeRoute
import com.navigation.NavRoute

fun NavGraphBuilder.buildNavigation() {
    screen<NavRoute.Splash>(
        enterTransition = { fadeIn(animationSpec = tween(NAV_ANIM_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(NAV_ANIM_DURATION)) }
    ) {
        BaseScreen(showTopBar = false) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }

    screen<NavRoute.Test> {
        BaseScreen {
            Text("TEST SCREEN")
        }
    }

    authRoute(fadeInDuration = NAV_ANIM_DURATION)
    homeRoute(fadeInDuration = NAV_ANIM_DURATION)
    profileRoute()
}