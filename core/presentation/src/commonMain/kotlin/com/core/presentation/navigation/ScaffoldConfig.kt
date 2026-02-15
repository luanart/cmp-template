package com.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

data class ScaffoldConfig(
    val fab: (@Composable () -> Unit)? = null,
    val topBar: (@Composable () -> Unit)? = null,
)

data class ScaffoldController(
    val setConfig: (key: String, config: ScaffoldConfig) -> Unit = { _, _ -> },
    val removeConfig: (key: String) -> Unit = {}
)

val LocalScaffoldController = compositionLocalOf { ScaffoldController() }