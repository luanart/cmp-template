package com.compose.app.util

import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import com.navigation.canGoBack

@Stable
class BackPressHandlers(
    private val navigator: NavController,
    private val showLeaveConfirmation: (Boolean) -> Unit
) {
    val backPressed: (Boolean) -> Unit = { needConfirmation ->
        when {
            needConfirmation -> {
                showLeaveConfirmation(true)
            }
            navigator.canGoBack() -> {
                navigator.popBackStack()
            }
        }
    }

    val cancelLeaving: () -> Unit = { showLeaveConfirmation(false) }

    val confirmLeaving: () -> Unit = {
        showLeaveConfirmation(false)
        navigator.popBackStack()
    }
}