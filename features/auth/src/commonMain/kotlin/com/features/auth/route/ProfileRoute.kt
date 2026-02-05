package com.features.auth.route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.features.auth.screen.profile.edit.EditProfileScreen
import com.features.auth.screen.profile.info.ProfileScreen
import com.navigation.NavRoute

fun NavGraphBuilder.profileRoute() {
    composable<NavRoute.Profile> { ProfileScreen() }
    composable<NavRoute.EditProfile> { EditProfileScreen() }
}