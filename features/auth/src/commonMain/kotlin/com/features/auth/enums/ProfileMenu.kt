package com.features.auth.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.ui.graphics.vector.ImageVector
import com.resources.Res
import com.resources.dark_mode
import com.resources.help_n_support
import com.resources.privacy_policy
import org.jetbrains.compose.resources.StringResource

enum class ProfileMenu(val type: ProfileMenuType, val isToggle: Boolean) {
    DARK_MODE(ProfileMenuType.PREFERENCE, true),
    HELP_N_SUPPORT(ProfileMenuType.OTHERS, false),
    PRIVACY_POLICY(ProfileMenuType.OTHERS, false);

    val image: ImageVector
        get() = when(this) {
            DARK_MODE -> Icons.Filled.DarkMode
            HELP_N_SUPPORT -> Icons.AutoMirrored.Default.Help
            PRIVACY_POLICY -> Icons.Filled.PrivacyTip
        }

    val labelRes: StringResource
        get() = when(this) {
            DARK_MODE -> Res.string.dark_mode
            HELP_N_SUPPORT -> Res.string.help_n_support
            PRIVACY_POLICY -> Res.string.privacy_policy
        }
}

enum class ProfileMenuType {
    OTHERS, PREFERENCE;
}