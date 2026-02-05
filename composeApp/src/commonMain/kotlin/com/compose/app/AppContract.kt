package com.compose.app

import androidx.compose.runtime.Immutable
import com.compose.app.enums.NavItem
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState
import com.navigation.NavRoute
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class AppState(
    val darkMode: Boolean = false,
    val navItems: PersistentList<NavItem> = NavItem.entries.toPersistentList(),
    val selectedNavItem: NavItem? = NavItem.HOME
) : ViewState

sealed interface AppAction : ViewAction {
    data class NavigateTo(val route: NavRoute) : AppAction
    data class UpdateSelectedNavItem(val item: NavItem?) : AppAction
}

sealed interface AppEffect : ViewEffect {
    data class NavigateToNavItem(val route: NavRoute) : AppEffect
    data class NavigateToStartDest(val route: NavRoute) : AppEffect
}