package com.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun ShowViewIf(visible: Boolean, content: @Composable () -> Unit) {
    if (visible) content()
}

@Suppress("ParamsComparedByRef")
@Composable
fun <T> LaunchedViewEffect(flow: Flow<T>, onCollect: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(onCollect)
        }
    }
}