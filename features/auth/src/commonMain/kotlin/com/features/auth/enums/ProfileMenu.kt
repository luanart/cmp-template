package com.features.auth.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.graphics.vector.ImageVector
import com.features.auth.screen.profile.info.ProfileAction
import com.features.auth.screen.profile.info.ProfileState
import com.resources.Res
import com.resources.dark_mode
import com.resources.help_n_support
import com.resources.language
import com.resources.others
import com.resources.preferences
import com.resources.privacy_policy
import org.jetbrains.compose.resources.StringResource

enum class ProfileMenu(val type: ProfileMenuType, val action: ProfileMenuAction) {
    CHANGE_LANGUAGE(ProfileMenuType.PREFERENCES, ProfileMenuAction.PREVIEW),
    DARK_MODE(ProfileMenuType.PREFERENCES, ProfileMenuAction.SWITCH),
    HELP_N_SUPPORT(ProfileMenuType.OTHERS, ProfileMenuAction.GENERAL),
    PRIVACY_POLICY(ProfileMenuType.OTHERS, ProfileMenuAction.GENERAL);

    val image: ImageVector
        get() = when(this) {
            CHANGE_LANGUAGE -> Icons.Filled.Translate
            DARK_MODE -> Icons.Filled.DarkMode
            HELP_N_SUPPORT -> Icons.AutoMirrored.Default.Help
            PRIVACY_POLICY -> Icons.Filled.PrivacyTip
        }

    val labelRes: StringResource
        get() = when(this) {
            CHANGE_LANGUAGE -> Res.string.language
            DARK_MODE -> Res.string.dark_mode
            HELP_N_SUPPORT -> Res.string.help_n_support
            PRIVACY_POLICY -> Res.string.privacy_policy
        }

    val enableClickable: Boolean
        get() = action != ProfileMenuAction.SWITCH

    fun resolveSwitch(
        state: ProfileState,
        dispatcher: (ProfileAction) -> Unit
    ) : Pair<Boolean, (Boolean) -> Unit>? {
        return when (this) {
            DARK_MODE -> {
                state.isDarkTheme to { isChecked ->
                    dispatcher(ProfileAction.MenuClicked(menu = this, checked = isChecked))
                }
            }
            else -> null
        }
    }

    fun getPreviewText(state: ProfileState): String {
        return when (this) {
            CHANGE_LANGUAGE -> state.language
            else -> "en-US"
        }
    }
}

enum class ProfileMenuType {
    OTHERS, PREFERENCES;

    val labelRes: StringResource
        get() = when(this) {
            OTHERS -> Res.string.others
            PREFERENCES -> Res.string.preferences
        }
}

enum class ProfileMenuAction {
    GENERAL, PREVIEW, SWITCH
}