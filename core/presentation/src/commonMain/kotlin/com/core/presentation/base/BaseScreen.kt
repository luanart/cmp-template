package com.core.presentation.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.component.SnackbarType
import com.core.presentation.data.AppError
import com.core.presentation.data.Confirmation
import com.core.presentation.navigation.LocalScaffoldController
import com.core.presentation.navigation.ScaffoldConfig
import com.core.presentation.util.ShowViewIf
import com.core.presentation.widget.ErrorStateView
import com.core.presentation.widget.LoadingDialog
import com.navigation.LocalNavBackStackEntry

@Composable
fun BaseScreen(
    error: AppError? = null,
    pageTitle: String? = null,
    showTopBar: Boolean = true,
    showLoading: Boolean = false,
    loadingText: String = "Please wait...",
    confirmOnBack: Boolean = false,
    backConfirmationData: Confirmation? = null,
    topBarActions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {

    val snackbarHostState = LocalSnackbarHostState.current
    val scaffoldController = LocalScaffoldController.current

    val entry = LocalNavBackStackEntry.current
    val entryId = entry?.id ?: "unknown"

    DisposableEffect(entryId) {
        scaffoldController.setConfig(entryId, ScaffoldConfig(fab = floatingActionButton))

        onDispose {
            scaffoldController.removeConfig(entryId)
        }
    }

    LaunchedEffect(error) {
        if (error != null && !error.fullPage) {
            snackbarHostState.showSnackbar(message = error.message, snackType = SnackbarType.ERROR)
            error.clearError?.invoke()
        }
    }

    if (error != null && error.fullPage && error.message.isNotBlank()) {
        ErrorStateView(error = error)
    } else {
        content()
    }

    if (showLoading) {
        LoadingDialog(text = loadingText)
    }
}