package com.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.core.presentation.data.Confirmation

data class ScaffoldConfig(
    val title: String? = "",
    val confirmExit: Boolean = false,
    val topBar: (@Composable () -> Unit)? = null,
    val floatingActionButton: (@Composable () -> Unit)? = null,
)

data class ScaffoldController(
    val setConfig: (key: String, config: ScaffoldConfig) -> Unit = { _, _ -> },
    val removeConfig: (key: String) -> Unit = {},
    val updateLeaveConfirmationData: (confirmation: Confirmation) -> Unit = {},
)

val LocalScaffoldController = compositionLocalOf { ScaffoldController() }