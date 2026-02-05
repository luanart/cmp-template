package com.compose.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.app.enums.NavItem
import com.core.presentation.util.ShowViewIf
import kotlinx.collections.immutable.PersistentList
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhoneNavigation(
    selected: NavItem?,
    navItems: PersistentList<NavItem>,
    primaryActionContent: (@Composable () -> Unit)? = null,
    onNavigate: (NavItem) -> Unit
) {
    ShowViewIf(visible = selected != null) {
        NavigationBar {
            navItems.forEachIndexed { index, item ->
                val middleIndex = navItems.size / 2
                val icon = if (selected == item) item.selectedIcon else item.unselectedIcon

                if (index == middleIndex) {
                    primaryActionContent?.let {
                        Box(
                            modifier = Modifier.size(48.dp)
                                .offset(y = (-4).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            it()
                        }
                    }
                }

                NavigationBarItem(
                    selected = selected == item,
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(item.labelRes)
                        )
                    },
                    label = {
                        Text(text = stringResource(item.labelRes))
                    },
                    onClick = {
                        onNavigate(item)
                    },
                )
            }
        }
    }
}