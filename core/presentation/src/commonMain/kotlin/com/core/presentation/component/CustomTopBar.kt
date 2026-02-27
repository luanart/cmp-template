package com.core.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun CustomTopBar(
    title: String,
    canGoBack: Boolean,
    onBackPressed: () -> Unit,
    topBarActions: @Composable (RowScope.() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            AnimatedContent(
                targetState = title,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)))
                        .togetherWith(fadeOut(animationSpec = tween(90)))
                },
                label = "TitleAnimation"
            ) { title ->
                if (title.isNotBlank()) {
                    Text(text = title)
                }
            }
        },
        navigationIcon = {
            AnimatedVisibility(
                visible = canGoBack,
                enter = fadeIn(tween(250)) + expandHorizontally(expandFrom = Alignment.Start),
                exit = fadeOut(tween(250)) + shrinkHorizontally(shrinkTowards = Alignment.Start)
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            topBarActions?.invoke(this)
        }
    )
}