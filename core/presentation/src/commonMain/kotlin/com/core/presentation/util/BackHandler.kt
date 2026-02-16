package com.core.presentation.util

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.core.presentation.data.Confirmation
import com.navigation.canGoBack

@Stable
class BackHandler(isTopNavigation: Boolean, private val navigator: NavController) {
    var canGoBack by mutableStateOf(navigator.canGoBack() && !isTopNavigation)
        private set

    var showConfirmation by mutableStateOf(false)
        private set

    var confirmationData by mutableStateOf(Confirmation.Default)
        private set

    val backPressed: (confirmExit: Boolean) -> Unit = { needConfirmation ->
        when {
            needConfirmation -> {
                showConfirmation = true
            }
            navigator.canGoBack() -> {
                navigator.popBackStack()
            }
        }
    }

    val cancelLeaving: () -> Unit = {
        showConfirmation = false
    }

    val confirmLeaving: () -> Unit = {
        showConfirmation = false
        navigator.popBackStack()
    }

    fun updateConfirmationData(data: Confirmation) {
        confirmationData = data
    }
}