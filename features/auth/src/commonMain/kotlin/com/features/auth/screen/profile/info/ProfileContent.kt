package com.features.auth.screen.profile.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.applyIf
import com.core.presentation.util.cardContainer
import com.features.auth.screen.profile.section.ProfileCard
import com.resources.Res
import com.resources.dark_mode
import com.resources.help_n_support
import com.resources.privacy_policy
import org.jetbrains.compose.resources.StringResource
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
                labelRes = Res.string.dark_mode,
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
                labelRes = Res.string.help_n_support,
                onClick = {

                }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = AppTheme.dimens.default))
            CardItem(
                icon = Icons.Filled.PrivacyTip,
                labelRes = Res.string.privacy_policy,
                onClick = {

                }
            )
        }
    }
}

@Composable
private fun CardItem(
    icon: ImageVector,
    labelRes: StringResource,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = stringResource(labelRes)
        )
    }
) {
    ListItem(
        modifier = Modifier.height(56.dp)
            .applyIf(condition = onClick != null) {
                then(Modifier.clickable(onClick = onClick!!))
            },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        headlineContent = {
            Text(
                text = stringResource(labelRes),
                fontWeight = FontWeight.Medium
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(labelRes)
            )
        },
        trailingContent = trailingContent
    )
}