package com.features.auth.screen.profile.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.presentation.component.LabeledRow
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.ShowViewIf
import com.core.presentation.extension.cardContainer
import com.core.presentation.widget.ConfirmationDialog
import com.features.auth.enums.ProfileMenu
import com.features.auth.enums.ProfileMenuAction
import com.features.auth.screen.profile.section.ProfileCard
import com.resources.Res
import com.resources.confirm_logout_message
import com.resources.confirm_logout_title
import com.resources.stay_here
import com.resources.yes_logout
import org.jetbrains.compose.resources.stringResource

@Preview(showBackground = true)
@Composable
internal fun ProfileContent(
    state: ProfileState = ProfileState(),
    dispatcher: (ProfileAction) -> Unit = {}
) {

    val groupedMenuItems = remember { ProfileMenu.entries.groupBy { it.type } }

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(all = AppTheme.dimens.default)
    ) {
        ProfileCard(
            user = state.user,
            loading = state.loading,
            onEditProfile = { dispatcher(ProfileAction.EditProfile) },
        )
        Spacer(modifier = Modifier.padding(top = AppTheme.dimens.small))

        groupedMenuItems.forEach { (type, menus) ->
            Text(
                text = stringResource(type.labelRes),
                style = AppTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = AppTheme.dimens.default)
                    .padding(bottom = AppTheme.dimens.small)
            )

            Column(
                modifier = Modifier.cardContainer(color = AppTheme.colors.surfaceContainer)
            ) {
                menus.forEachIndexed { index, menu ->
                    LabeledRow(
                        label = stringResource(menu.labelRes),
                        image = menu.image,
                        trailingContent = {
                            MenuAction(
                                menu = menu,
                                previewText = menu.getPreviewText(state),
                                switchState = menu.resolveSwitch(state, dispatcher)
                            )
                        },
                        modifier = Modifier.clickable(enabled = menu.enableClickable) {
                            dispatcher(ProfileAction.MenuClicked(menu = menu))
                        },
                    )
                    ShowViewIf(visible = menus.lastIndex != index) {
                        HorizontalDivider(modifier = Modifier.padding(start = 52.dp, end = 16.dp))
                    }
                }
            }
        }
    }

    ShowViewIf(visible = state.confirmLogout) {
        ConfirmationDialog(
            title = stringResource(Res.string.confirm_logout_title),
            message = stringResource(Res.string.confirm_logout_message),
            actions = { modifier ->
                Button(
                    onClick = { dispatcher(ProfileAction.Logout) },
                    modifier = modifier
                ) {
                    Text(text = stringResource(Res.string.yes_logout))
                }
                OutlinedButton(
                    onClick = {
                        dispatcher(ProfileAction.ToggleConfirmLogout(confirm = false))
                    },
                    modifier = modifier
                ) {
                    Text(text = stringResource(Res.string.stay_here))
                }
            }
        )
    }
}

@Composable
private fun MenuAction(
    menu: ProfileMenu,
    previewText: String,
    switchState: Pair<Boolean, (Boolean) -> Unit>?
) {
    when(menu.action) {
        ProfileMenuAction.GENERAL -> {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(menu.labelRes)
            )
        }
        ProfileMenuAction.PREVIEW -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = previewText)
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = stringResource(menu.labelRes))
            }
        }
        ProfileMenuAction.SWITCH -> {
            switchState?.let { (isChecked, onCheckedChange) ->
                Switch(checked = isChecked, onCheckedChange = onCheckedChange)
            }
        }
    }
}