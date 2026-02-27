package com.core.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationEventHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.core.presentation.component.CustomSnackbarHost
import com.core.presentation.component.CustomSnackbarHostState
import com.core.presentation.component.CustomTopBar
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.util.BackHandler
import com.core.presentation.util.ShowViewIf
import com.core.presentation.widget.ConfirmationLeavePage
import com.navigation.LocalNavigator

@Composable
fun AppScaffold(
    backHandler: BackHandler,
    navigation: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val navigator = LocalNavigator.current
    val snackbarState = remember { CustomSnackbarHostState() }
    val navEventState = rememberNavigationEventState(currentInfo = NavigationEventInfo.None)

    val entry by navigator.currentBackStackEntryAsState()
    val entryId = entry?.id.orEmpty()

    val configs = remember { mutableStateMapOf<String, ScaffoldConfig>() }
    val currentConfig = configs[entryId] ?: ScaffoldConfig()

    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbarState,
        LocalScaffoldController provides ScaffoldController(
            setConfig = { key, config -> configs[key] = config },
            removeConfig = { key -> configs.remove(key) },
            updateLeaveConfirmationData = { data -> backHandler.updateConfirmationData(data) }
        )
    ) {
        Scaffold(
            bottomBar = navigation,
            topBar = {
                currentConfig.title?.let {
                    CustomTopBar(
                        title = it,
                        backHandler = backHandler,
                        confirmExit = currentConfig.confirmExit,
                        topBarActions = currentConfig.topBarActions
                    )
                }
            },
            floatingActionButton = {
                currentConfig.floatingActionButton?.invoke()
            },
            snackbarHost = {
                CustomSnackbarHost(state = snackbarState, modifier = Modifier.imePadding())
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues = it)
            ) {
                content()
            }

            ShowViewIf(visible = currentConfig.confirmExit) {
                NavigationEventHandler(
                    state = navEventState,
                    onBackCompleted = { backHandler.backPressed(true) }
                )
            }

            ShowViewIf(visible = backHandler.showConfirmation) {
                ConfirmationLeavePage(
                    data = backHandler.confirmationData,
                    onCancel = backHandler.cancelLeaving,
                    onConfirm = backHandler.confirmLeaving,
                )
            }
        }
    }
}