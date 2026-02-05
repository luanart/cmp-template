package com.core.presentation.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.component.SnackbarType
import com.core.presentation.data.AppError
import com.core.presentation.data.Confirmation
import com.core.presentation.data.ScreenConfig
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.widget.ErrorStateView
import com.core.presentation.widget.LoadingDialog

@Composable
fun BaseScreen(
    error: AppError? = null,
    pageTitle: String = "",
    showTopBar: Boolean = true,
    showLoading: Boolean = false,
    loadingText: String = "Please wait...",
    confirmOnBack: Boolean = false,
    backConfirmationData: Confirmation? = null,
    topBarActions: @Composable (RowScope.() -> Unit)? = null,
    floatingButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val scaffoldState = LocalScaffoldState.current
    val snackbarHostState = LocalSnackbarHostState.current

    val currentTopBarActions by rememberUpdatedState(topBarActions)
    val currentFloatingButton by rememberUpdatedState(floatingButton)

    SideEffect {
        scaffoldState.updateConfig(
            config = ScreenConfig(
                pageTitle = pageTitle,
                showTopBar = showTopBar,
                confirmOnBack = confirmOnBack,
                topBarActions = currentTopBarActions,
                floatingButton = currentFloatingButton
            )
        )
    }

    LaunchedEffect(backConfirmationData) {
        backConfirmationData?.let { scaffoldState.setBackConfirmationData(it) }
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