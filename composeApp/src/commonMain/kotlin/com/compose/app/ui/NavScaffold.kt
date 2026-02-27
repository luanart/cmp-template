package com.compose.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.app.ui.navigation.NavItem
import com.compose.app.ui.navigation.PhoneNavigation
import com.compose.app.ui.navigation.TabletNavigation
import com.core.presentation.data.ScreenType
import com.core.presentation.navigation.AppScaffold
import com.core.presentation.util.rememberBackHandler
import com.core.presentation.util.rememberScreenType
import com.navigation.LocalNavigator
import kotlinx.collections.immutable.PersistentList

@Composable
fun NavScaffold(
    navItems: PersistentList<NavItem>,
    selected: NavItem?,
    onNavigate: (NavItem) -> Unit,
    screenType: ScreenType = rememberScreenType(),
    primaryActionContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {

    val navigator = LocalNavigator.current
    val backHandler = rememberBackHandler(navigator = navigator)

    when(screenType) {
        ScreenType.Compact -> {
            AppScaffold(
                isTopNavigation = selected != null,
                backHandler = backHandler,
                navigation = {
                    PhoneNavigation(
                        selected = selected,
                        navItems = navItems,
                        onNavigate = onNavigate,
                        primaryActionContent = primaryActionContent,
                    )
                },
                content = content
            )
        }
        else -> {
            Row(
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding()
            ) {
                AnimatedVisibility(visible = selected != null) {
                    TabletNavigation(
                        selected = selected,
                        navItems = navItems,
                        onNavigate = onNavigate,
                        primaryActionContent = primaryActionContent,
                    )
                }
                AppScaffold(
                    isTopNavigation = selected != null,
                    backHandler = backHandler,
                    content = content
                )
            }
        }
    }
}