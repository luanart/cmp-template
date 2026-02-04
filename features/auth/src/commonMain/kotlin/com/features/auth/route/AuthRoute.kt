package com.features.auth.route

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.features.auth.screen.login.LoginScreen
import com.features.auth.screen.profile.edit.EditProfileScreen
import com.features.auth.screen.profile.info.ProfileScreen
import com.features.auth.screen.register.RegisterScreen
import com.navigation.NavRoute

fun NavGraphBuilder.authRoute() {
    composable<NavRoute.Login>(
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        }
    ) {
        LoginScreen()
    }
    composable<NavRoute.Register> { RegisterScreen() }
    composable<NavRoute.Profile> { ProfileScreen() }
    composable<NavRoute.EditProfile> { EditProfileScreen() }
}