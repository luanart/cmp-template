package com.features.auth.route

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavGraphBuilder
import com.core.presentation.extension.screen
import com.features.auth.screen.login.LoginScreen
import com.features.auth.screen.register.RegisterScreen
import com.navigation.NavRoute

fun NavGraphBuilder.authRoute(fadeInDuration: Int) {
    screen<NavRoute.Login>(
        enterTransition = { fadeIn(animationSpec = tween(fadeInDuration)) }
    ) {
        LoginScreen()
    }
    screen<NavRoute.Register> { RegisterScreen() }
}