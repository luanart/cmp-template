package com.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.core.presentation.data.Confirmation
import com.navigation.canGoBack

@Stable
class BackHandler(initialShowConfirmation: Boolean = false, private val navigator: NavController) {
    var showConfirmation by mutableStateOf(initialShowConfirmation)
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

@Suppress("ParamsComparedByRef")
@Composable
fun rememberBackHandler(navigator: NavController): BackHandler {
    return rememberSaveable(
        navigator,
        saver = Saver(
            save = { listOf(it.showConfirmation) },
            restore = { BackHandler(initialShowConfirmation = it[0], navigator = navigator) }
        )
    ) {
        BackHandler(navigator = navigator)
    }
}