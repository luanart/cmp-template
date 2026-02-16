package com.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.core.presentation.theme.AppTheme

enum class SnackbarType {
    INFO, ERROR, SUCCESS
}

@Stable
class CustomSnackbarHostState {
    val hostState = SnackbarHostState()
    var currentType by mutableStateOf(SnackbarType.INFO)
        private set

    var iconVector by mutableStateOf<ImageVector?>(null)
        private set

    suspend fun show(
        message: String,
        snackType: SnackbarType = SnackbarType.INFO,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        icon: ImageVector? = null,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    ): SnackbarResult {
        currentType = snackType
        iconVector = icon

        return hostState.showSnackbar(
            message = message,
            actionLabel= actionLabel,
            withDismissAction = withDismissAction,
            duration = duration
        )
    }
}

@Composable
fun CustomSnackbarHost(
    state: CustomSnackbarHostState,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = SnackbarDefaults.shape,
    actionColor: Color = SnackbarDefaults.actionColor,
    actionContentColor: Color = SnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
) {
    SnackbarHost(hostState = state.hostState, modifier = modifier) { data ->
        val (containerColor, contentColor) = when (state.currentType) {
            SnackbarType.INFO -> SnackbarDefaults.color to SnackbarDefaults.contentColor
            SnackbarType.ERROR -> AppTheme.colors.error to AppTheme.colors.onError
            SnackbarType.SUCCESS -> AppTheme.extendedColors.success to AppTheme.extendedColors.onSuccess
        }

        val actionLabel = data.visuals.actionLabel
        val actionComposable: (@Composable () -> Unit)? =
            if (actionLabel != null) {
                @Composable {
                    TextButton(
                        colors = ButtonDefaults.textButtonColors(contentColor = actionColor),
                        onClick = { data.performAction() },
                        content = { Text(actionLabel) },
                    )
                }
            } else {
                null
            }

        val dismissActionComposable: (@Composable () -> Unit)? =
            if (data.visuals.withDismissAction) {
                @Composable {
                    IconButton(
                        onClick = { data.dismiss() },
                        content = {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Dismiss")
                        },
                    )
                }
            } else {
                null
            }

        Snackbar(
            modifier = modifier.padding(all = AppTheme.dimens.medium),
            action = actionComposable,
            dismissAction = dismissActionComposable,
            actionOnNewLine = actionOnNewLine,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            actionContentColor = actionContentColor,
            dismissActionContentColor = dismissActionContentColor,
            content = {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    state.iconVector?.let { icon ->
                        Icon(imageVector = icon, contentDescription = null)
                    }
                    Text(text = data.visuals.message)
                }
            },
        )
    }
}

val LocalSnackbarHostState = compositionLocalOf<CustomSnackbarHostState> {
    error("SnackbarHostState not found!")
}