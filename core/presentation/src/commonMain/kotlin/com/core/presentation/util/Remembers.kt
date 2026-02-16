package com.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.core.presentation.data.ScreenType
import kotlin.math.max

@Composable
fun rememberGridColumns(maxColumnWidth: Dp = 250.dp): Int {
    val density = LocalDensity.current
    val windowWidth = LocalWindowInfo.current.containerSize.width

    return remember(windowWidth) {
        val widthDp = with(density) { windowWidth.toDp() }
        max((widthDp / maxColumnWidth).toInt(), 1)
    }
}

@Composable
fun rememberScreenSize(): DpSize {
    val density = LocalDensity.current
    val windowSize = LocalWindowInfo.current.containerSize

    return remember(windowSize) {
        val widthDp = with(density) { windowSize.width.toDp() }
        val heightDp = with(density) { windowSize.height.toDp() }

        DpSize(widthDp, heightDp)
    }
}

@Composable
fun rememberScreenType(): ScreenType {
    val screenSize = rememberScreenSize()

    return remember(screenSize) {
        when {
            screenSize.width < 600.dp -> ScreenType.Compact
            screenSize.width < 840.dp || screenSize.width < 840.dp -> ScreenType.Medium
            else -> ScreenType.Expanded
        }
    }
}