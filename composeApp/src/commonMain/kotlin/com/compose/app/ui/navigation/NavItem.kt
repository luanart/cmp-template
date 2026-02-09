package com.compose.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.navigation.NavRoute
import com.navigation.isRouteInHierarchy
import com.resources.Res
import com.resources.home
import com.resources.my_profile
import org.jetbrains.compose.resources.StringResource

enum class NavItem(
    val route: NavRoute,
    val labelRes: StringResource,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    HOME(
        route = NavRoute.Home,
        labelRes = Res.string.home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    PROFILE(
        route = NavRoute.Profile,
        labelRes = Res.string.my_profile,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
    );

    companion object {
        fun List<NavItem>.activeItem(entry: NavBackStackEntry?) = firstOrNull {
            entry?.isRouteInHierarchy(it.route::class) == true
        }
    }
}