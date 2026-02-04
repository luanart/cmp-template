package com.features.auth.screen.profile.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.core.presentation.component.LabeledRow
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.applyIf
import com.core.presentation.util.cardContainer
import com.features.auth.screen.profile.section.ProfileCard
import com.resources.Res
import com.resources.dark_mode
import com.resources.help_n_support
import com.resources.privacy_policy
import org.jetbrains.compose.resources.stringResource

@Preview(showBackground = true)
@Composable
internal fun ProfileContent(
    state: ProfileState = ProfileState(),
    onAction: (ProfileAction) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(all = AppTheme.dimens.default),
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.default)
    ) {
        ProfileCard(
            user = state.user,
            onEditProfile = { onAction(ProfileAction.EditProfile) },
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.cardContainer(color = AppTheme.colors.surfaceContainer)
        ) {
            CardItem(
                icon = Icons.Filled.DarkMode,
                label = stringResource(Res.string.dark_mode),
                trailingContent = {
                    Switch(
                        checked = state.isDarkTheme,
                        onCheckedChange = { onAction(ProfileAction.ToggleDarkTheme(isDark = it)) }
                    )
                }
            )
        }
        Column(
            modifier = Modifier.cardContainer(color = AppTheme.colors.surfaceContainer)
        ) {
            CardItem(
                icon = Icons.AutoMirrored.Default.Help,
                label = stringResource(Res.string.help_n_support),
                onClick = {

                }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = AppTheme.dimens.default))
            CardItem(
                icon = Icons.Filled.PrivacyTip,
                label = stringResource(Res.string.privacy_policy),
                onClick = {

                }
            )
        }
    }
}

@Composable
private fun CardItem(
    icon: ImageVector,
    label: String,
    caption: String? = null,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = label
        )
    }
) {
    LabeledRow(
        label = label,
        image = icon,
        caption = caption,
        trailingContent = trailingContent,
        modifier = Modifier.applyIf(onClick != null) {
            clickable { onClick?.invoke() }
        },
    )
}