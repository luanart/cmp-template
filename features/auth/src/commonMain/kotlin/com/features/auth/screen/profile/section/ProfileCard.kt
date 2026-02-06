package com.features.auth.screen.profile.section

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.presentation.component.LabeledRow
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.cardContainer
import com.features.auth.data.model.User
import com.resources.Res
import com.resources.edit_profile
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.crossfade.CrossfadePlugin
import com.skydoves.landscapist.image.LandscapistImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileCard(
    user: User,
    loading: Boolean,
    onEditProfile: () -> Unit,
) {
    LabeledRow(
        label = user.name,
        caption = user.email,
        modifier = Modifier.cardContainer(color = AppTheme.colors.surfaceContainer),
        leadingContent = {
            LandscapistImage(
                imageModel = { user.image },
                modifier = Modifier.size(48.dp)
                    .clip(CircleShape),
                component = rememberImageComponent {
                    +ShimmerPlugin()
                    +CrossfadePlugin(duration = 550)
                }
            )
        },
        trailingContent = {
            IconButton(onClick = onEditProfile, enabled = !loading) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(Res.string.edit_profile)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    ProfileCard(
        user = User(name = "John Doe", email = "johndoe@example.com"),
        loading = true,
        onEditProfile = {}
    )
}