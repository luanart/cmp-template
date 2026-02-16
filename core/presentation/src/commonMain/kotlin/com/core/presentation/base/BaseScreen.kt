package com.core.presentation.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.common.error.AppException
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.component.SnackbarType
import com.core.presentation.data.Confirmation
import com.core.presentation.extension.toSnackbarMessage
import com.core.presentation.navigation.LocalScaffoldController
import com.core.presentation.navigation.ScaffoldConfig
import com.core.presentation.util.LaunchedViewEffect
import com.core.presentation.util.ShowViewIf
import com.core.presentation.widget.ErrorStateView
import com.core.presentation.widget.LoadingDialog
import com.navigation.LocalNavBackStackEntry
import com.resources.Res
import com.resources.err_no_internet
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString

@Suppress("ParamsComparedByRef")
@Composable
fun <S: ViewState, A: ViewAction, E: ViewEffect> BaseScreen(
    viewModel: BaseViewModel<S, A, E>,
    pageTitle: String? = "",
    confirmExit: Boolean = false,
    leaveConfirmation: Confirmation? = null,
    onLaunchEffect: suspend (E) -> Unit = {},
    topBar: (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable (state: S, dispatcher: (A) -> Unit) -> Unit
) {
    val entryId = LocalNavBackStackEntry.current?.id ?: "unknown"

    val state by viewModel.state.collectAsStateWithLifecycle()
    var pageError by rememberSaveable { mutableStateOf<String?>(null) }

    val retryHandler: () -> Unit = {
        pageError = null
        viewModel.retry()
    }

    BaseScreenScaffoldConfig(
        entryId,
        pageTitle,
        confirmExit,
        leaveConfirmation,
        topBar,
        floatingActionButton
    )

    LaunchedViewEffect(viewModel.effect, onLaunchEffect)

    HandleBaseErrors(
        errorFlow = viewModel.error,
        onNoInternet = { pageError = it }
    )

    content(state, viewModel::handleAction)

    BaseScreenOverlays(
        pageError = pageError,
        onRetry = retryHandler,
        loading = state.pageLoading
    )
}


@Composable
fun BaseScreen(
    pageTitle: String? = null,
    pageLoading: Boolean = false,
    confirmExit: Boolean = false,
    leaveConfirmation: Confirmation? = null,
    topBar: (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val entryId = LocalNavBackStackEntry.current?.id ?: "unknown"

    BaseScreenScaffoldConfig(
        entryId,
        pageTitle,
        confirmExit,
        leaveConfirmation,
        topBar,
        floatingActionButton
    )

    content()

    ShowViewIf(pageLoading) {
        LoadingDialog(text = "Please wait...")
    }
}


@Composable
private fun BaseScreenScaffoldConfig(
    entryId: String,
    pageTitle: String?,
    confirmExit: Boolean,
    leaveConfirmation: Confirmation?,
    topBar: (@Composable () -> Unit)?,
    floatingActionButton: (@Composable () -> Unit)?
) {
    val scaffold = LocalScaffoldController.current

    DisposableEffect(entryId) {
        scaffold.setConfig(
            entryId,
            ScaffoldConfig(
                title = pageTitle,
                confirmExit = confirmExit,
                topBar = topBar,
                floatingActionButton = floatingActionButton,
            )
        )

        leaveConfirmation?.let {
            scaffold.updateLeaveConfirmationData(it)
        }

        onDispose { scaffold.removeConfig(entryId) }
    }
}

@Suppress("ParamsComparedByRef")
@Composable
private fun HandleBaseErrors(
    errorFlow: Flow<Throwable>,
    onNoInternet: (String) -> Unit
) {
    val snackbar = LocalSnackbarHostState.current

    LaunchedViewEffect(flow = errorFlow) { error ->
        when (error) {
            is AppException.NoInternet ->
                onNoInternet(getString(Res.string.err_no_internet))

            else ->
                snackbar.show(
                    message = error.toSnackbarMessage(),
                    snackType = SnackbarType.ERROR
                )
        }
    }
}

@Composable
private fun BaseScreenOverlays(
    pageError: String?,
    onRetry: () -> Unit,
    loading: Boolean
) {
    AnimatedVisibility(
        visible = !pageError.isNullOrBlank(),
        enter = fadeIn() + scaleIn(initialScale = 0.98f),
        exit = fadeOut() + scaleOut(targetScale = 0.98f)
    ) {
        pageError?.let {
            ErrorStateView(message = it, onRetry = onRetry)
        }
    }

    ShowViewIf(visible = loading) {
        LoadingDialog(text = "Please wait...")
    }
}