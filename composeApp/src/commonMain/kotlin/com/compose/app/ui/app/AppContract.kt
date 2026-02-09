package com.compose.app.ui.app

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
    data class UpdateSelectedAppItem(val item: NavItem?) : AppAction
}

sealed interface NavEffect : ViewEffect {
    data class NavigateToNavItem(val route: NavRoute) : NavEffect
    data class NavigateToStartDest(val route: NavRoute) : NavEffect
}