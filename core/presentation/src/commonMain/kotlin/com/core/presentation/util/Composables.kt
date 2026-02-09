package com.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.core.presentation.base.ViewEffect
import com.core.presentation.data.ScreenType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
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

@Composable
fun <T> rememberItemsOrDummies(
    items: PersistentList<T>,
    loading: Boolean,
    dummySize: Int = 5,
    dummyInit: (Int) -> T,
): PersistentList<T> {

    val dummyItems = remember(dummySize) {
        List(dummySize, dummyInit).toPersistentList()
    }

    return remember { derivedStateOf { if (loading) dummyItems else items } }.value
}

@Composable
fun ShowViewIf(visible: Boolean, content: @Composable () -> Unit) {
    if (visible) content()
}

@Suppress("ParamsComparedByRef")
@Composable
fun <T : ViewEffect> LaunchedViewEffect(effect: Flow<T>, onEvent: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(effect, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            effect.collect(onEvent)
        }
    }
}