package com.compose.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.core.presentation.theme.AppTheme
import kotlinx.collections.immutable.PersistentList
import org.jetbrains.compose.resources.stringResource

@Composable
fun TabletNavigation(
    selected: NavItem?,
    navItems: PersistentList<NavItem>,
    onNavigate: (NavItem) -> Unit,
    primaryActionContent: (@Composable () -> Unit)? = null,
) {
    NavigationRail(
        header = {
            primaryActionContent?.let { content ->
                Box(
                    modifier = Modifier.padding(top = AppTheme.dimens.medium),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    ) {
        navItems.forEach {
            NavigationRailItem(
                selected = selected == it,
                icon = {
                    Icon(
                        imageVector = if (selected == it) it.selectedIcon else it.unselectedIcon,
                        contentDescription = stringResource(it.labelRes)
                    )
                },
                label = {
                    Text(text = stringResource(it.labelRes))
                },
                onClick = { onNavigate(it) },
            )
        }
    }
}