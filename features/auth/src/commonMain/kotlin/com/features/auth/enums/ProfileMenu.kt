package com.features.auth.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.ui.graphics.vector.ImageVector
import com.features.auth.screen.profile.info.ProfileAction
import com.features.auth.screen.profile.info.ProfileState
import com.resources.Res
import com.resources.dark_mode
import com.resources.delete_account
import com.resources.help_n_support
import com.resources.language
import com.resources.logout
import com.resources.others
import com.resources.preferences
import com.resources.privacy_policy
import com.resources.security
import org.jetbrains.compose.resources.StringResource

enum class ProfileMenu(val type: ProfileMenuType, val action: ProfileMenuAction) {
    DARK_MODE(ProfileMenuType.PREFERENCES, ProfileMenuAction.SWITCH),
    CHANGE_LANGUAGE(ProfileMenuType.PREFERENCES, ProfileMenuAction.PREVIEW),
    HELP_N_SUPPORT(ProfileMenuType.OTHERS, ProfileMenuAction.GENERAL),
    PRIVACY_POLICY(ProfileMenuType.OTHERS, ProfileMenuAction.GENERAL),
    DELETE_ACCOUNT(ProfileMenuType.SECURITY, ProfileMenuAction.GENERAL),
    LOGOUT(ProfileMenuType.SECURITY, ProfileMenuAction.GENERAL);

    val image: ImageVector
        get() = when(this) {
            CHANGE_LANGUAGE -> Icons.Filled.Language
            DARK_MODE -> Icons.Filled.DarkMode
            HELP_N_SUPPORT -> Icons.AutoMirrored.Filled.Help
            PRIVACY_POLICY -> Icons.Filled.PrivacyTip
            DELETE_ACCOUNT -> Icons.Filled.Delete
            LOGOUT -> Icons.AutoMirrored.Filled.Logout
        }

    val labelRes: StringResource
        get() = when(this) {
            CHANGE_LANGUAGE -> Res.string.language
            DARK_MODE -> Res.string.dark_mode
            HELP_N_SUPPORT -> Res.string.help_n_support
            PRIVACY_POLICY -> Res.string.privacy_policy
            DELETE_ACCOUNT -> Res.string.delete_account
            LOGOUT -> Res.string.logout
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
    OTHERS, PREFERENCES, SECURITY;

    val labelRes: StringResource
        get() = when(this) {
            OTHERS -> Res.string.others
            PREFERENCES -> Res.string.preferences
            SECURITY -> Res.string.security
        }
}

enum class ProfileMenuAction {
    GENERAL, PREVIEW, SWITCH
}