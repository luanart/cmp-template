package com.features.auth.route

import androidx.navigation.NavGraphBuilder
import com.core.presentation.extension.screen
import com.features.auth.screen.profile.edit.EditProfileScreen
import com.features.auth.screen.profile.info.ProfileScreen
import com.navigation.NavRoute

fun NavGraphBuilder.profileRoute() {
    screen<NavRoute.Profile> { ProfileScreen() }
    screen<NavRoute.EditProfile> { EditProfileScreen() }
}