package com.compose.app.ui.layout

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationEventHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.compose.app.util.BackPressHandlers
import com.core.presentation.component.CustomSnackbarHost
import com.core.presentation.component.CustomSnackbarHostState
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.util.ScaffoldState
import com.core.presentation.util.ShowViewIf
import com.core.presentation.widget.ConfirmationLeavePage
import com.navigation.LocalNavigator

@Composable
fun BasicLayout(
    state: ScaffoldState,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val navigator = LocalNavigator.current
    val snackbarState = remember { CustomSnackbarHostState() }
    val backPressHandlers = remember(navigator) {
        BackPressHandlers(navigator, state::showLeaveConfirmation)
    }

    val navEventState = rememberNavigationEventState(currentInfo = NavigationEventInfo.None)

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarState) {
        Scaffold(
            bottomBar = bottomBar,
            floatingActionButton = {
                state.screenConfig.floatingButton?.invoke()
            },
            snackbarHost = {
                CustomSnackbarHost(state = snackbarState, modifier = Modifier.imePadding())
            },
        ) {
            content()

            NavigationEventHandler(
                state = navEventState,
                onBackCompleted = {
                    backPressHandlers.backPressed(state.screenConfig.confirmOnBack)
                }
            )

            ShowViewIf(visible = state.showLeaveConfirmation) {
                ConfirmationLeavePage(
                    data = state.confirmationData,
                    onCancel = backPressHandlers.cancelLeaving,
                    onConfirm = backPressHandlers.confirmLeaving,
                )
            }
        }
    }
}