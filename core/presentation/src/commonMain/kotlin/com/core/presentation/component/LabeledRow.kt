package com.core.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.ShowViewIfNotNull
import com.core.presentation.util.applyIf

@Composable
fun LabeledRow(
    label: String,
    modifier: Modifier = Modifier,
    image: ImageVector? = null,
    caption: String? = null,
    leadingContent: @Composable (() -> Unit)? = {
        ShowViewIfNotNull(value = image) {
            Icon(imageVector = it, contentDescription = label)
        }
    },
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .animateContentSize()
            .applyIf(condition = caption == null) {
                height(height = 56.dp)
            }
            .applyIf(condition = caption != null) {
                heightIn(min = 56.dp)
            }
            .then(other = modifier)
            .then(other = Modifier
                .padding(horizontal = AppTheme.dimens.default)
                .padding(vertical = AppTheme.dimens.medium)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.medium)
    ) {
        leadingContent?.invoke()
        Column(modifier = Modifier.weight(weight = 1f)) {
            Text(text = label, fontWeight = FontWeight(450))
            ShowViewIfNotNull(value = caption) {
                CaptionText(
                    value = it,
                    color = AppTheme.colors.onSurfaceVariant,
                )
            }
        }
        trailingContent?.invoke()
    }
}