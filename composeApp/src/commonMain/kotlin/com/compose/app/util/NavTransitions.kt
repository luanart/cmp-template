package com.compose.app.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.IntOffset

private const val NAV_ANIM_DURATION = 350

object NavTransitions {

    private val slideSpec = tween<IntOffset>(
        durationMillis = NAV_ANIM_DURATION,
        easing = FastOutSlowInEasing
    )

    private val fadeSpec = tween<Float>(
        durationMillis = NAV_ANIM_DURATION
    )

    fun enter(): EnterTransition =
        slideInHorizontally(
            initialOffsetX = { it / 3 },
            animationSpec = slideSpec
        ) + fadeIn(fadeSpec)

    fun exit(): ExitTransition =
        slideOutHorizontally(
            targetOffsetX = { -it / 3 },
            animationSpec = slideSpec
        ) + fadeOut(fadeSpec)

    fun popEnter(): EnterTransition =
        slideInHorizontally(
            initialOffsetX = { -it / 3 },
            animationSpec = slideSpec
        ) + fadeIn(fadeSpec)

    fun popExit(): ExitTransition =
        slideOutHorizontally(
            targetOffsetX = { it / 3 },
            animationSpec = slideSpec
        ) + fadeOut(fadeSpec)
}