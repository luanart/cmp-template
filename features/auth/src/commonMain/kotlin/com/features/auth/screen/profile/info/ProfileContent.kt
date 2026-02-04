package com.features.auth.screen.profile.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.presentation.component.LabeledRow
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.ShowViewIf
import com.core.presentation.util.cardContainer
import com.features.auth.enums.ProfileMenu
import com.features.auth.screen.profile.section.ProfileCard
import org.jetbrains.compose.resources.stringResource

@Preview(showBackground = true)
@Composable
internal fun ProfileContent(
    state: ProfileState = ProfileState(),
    onAction: (ProfileAction) -> Unit = {}
) {

    val groupedMenuItems = remember { ProfileMenu.entries.groupBy { it.type } }

    Column(modifier = Modifier.fillMaxSize().padding(all = AppTheme.dimens.default)) {
        ProfileCard(
            user = state.user,
            onEditProfile = { onAction(ProfileAction.EditProfile) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(top = AppTheme.dimens.small))

        groupedMenuItems.values.forEach { menus ->
            Spacer(modifier = Modifier.padding(top = AppTheme.dimens.default))

            Column(
                modifier = Modifier.cardContainer(color = AppTheme.colors.surfaceContainer)
            ) {
                menus.forEachIndexed { index, menu ->
                    LabeledRow(
                        label = stringResource(menu.labelRes),
                        image = menu.image,
                        trailingContent = {
                            if (menu.isToggle) {
                                Switch(
                                    checked = state.isDarkTheme,
                                    onCheckedChange = {
                                        onAction(ProfileAction.MenuClicked(menu = menu, checked = it))
                                    }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = stringResource(menu.labelRes)
                                )
                            }
                        },
                        modifier = Modifier.clickable(enabled = !menu.isToggle) {
                            onAction(ProfileAction.MenuClicked(menu = menu))
                        },
                    )
                    ShowViewIf(visible = menus.lastIndex != index) {
                        HorizontalDivider(modifier = Modifier.padding(start = 52.dp, end = 16.dp))
                    }
                }
            }
        }
    }
}