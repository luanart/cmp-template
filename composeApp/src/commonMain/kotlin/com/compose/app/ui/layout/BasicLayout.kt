package com.compose.app.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationEventHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.compose.app.enums.NavItem
import com.compose.app.util.BackPressHandlers
import com.core.presentation.component.CustomSnackbarHost
import com.core.presentation.component.CustomSnackbarHostState
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.util.ScaffoldState
import com.core.presentation.util.ShowViewIf
import com.core.presentation.widget.ConfirmationLeavePage
import com.navigation.LocalNavigator
import com.navigation.canGoBack

@Composable
fun BasicLayout(
    state: ScaffoldState,
    navItem: NavItem?,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val navigator = LocalNavigator.current
    val snackbarState = remember { CustomSnackbarHostState() }
    val backPressHandlers = remember(navigator) {
        BackPressHandlers(navigator, state::showLeaveConfirmation)
    }

    val navEventState = rememberNavigationEventState(currentInfo = NavigationEventInfo.None)
    val onBackPressed = {
        backPressHandlers.backPressed(state.screenConfig.confirmOnBack)
    }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarState) {
        Scaffold(
            topBar = {
                if (state.screenConfig.showTopBar) {
                    TopAppBar(
                        title = { Text(state.screenConfig.pageTitle) },
                        actions = { state.screenConfig.topBarActions?.invoke(this) },
                        navigationIcon = {
                            if (navigator.canGoBack() && navItem == null) {
                                IconButton(onClick = onBackPressed) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back to previous page"
                                    )
                                }
                            }
                        }
                    )
                }
            },
            bottomBar = bottomBar,
            floatingActionButton = {
                state.screenConfig.floatingButton?.invoke()
            },
            snackbarHost = {
                CustomSnackbarHost(state = snackbarState, modifier = Modifier.imePadding())
            },
        ) {
            Box(modifier = Modifier.padding(paddingValues = it)) {
                content()
            }

            NavigationEventHandler(state = navEventState, onBackCompleted = onBackPressed)

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