package com.features.auth.screen.profile.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.core.presentation.theme.AppTheme
import com.features.auth.screen.profile.section.ProfileCard

@Preview(showBackground = true)
@Composable
internal fun ProfileContent(
    state: ProfileState = ProfileState(),
    onAction: (ProfileAction) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.default)
    ) {
        ProfileCard(
            user = state.user,
            onEditProfile = { onAction(ProfileAction.EditProfile) },
            modifier = Modifier.fillMaxWidth()
                .padding(all = AppTheme.dimens.default)
        )
    }
}