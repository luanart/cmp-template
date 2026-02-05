package com.compose.app

import androidx.lifecycle.viewModelScope
import com.compose.app.AppEffect.NavigateToNavItem
import com.compose.app.AppEffect.NavigateToStartDest
import com.core.data.local.LocalStorage
import com.core.presentation.base.BaseViewModel
import com.navigation.startDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class AppViewModel(
    private val localStorage: LocalStorage
) : BaseViewModel<AppState, AppAction, AppEffect>(
    initialState = AppState()
) {
    override fun loadInitialData() {
        initializeDarkMode()
        initializeStartDestination()
    }

    override fun handleAction(action: AppAction) {
        when(action) {
            is AppAction.NavigateTo -> postEffect(effect = NavigateToNavItem(action.route))
            is AppAction.UpdateSelectedNavItem -> updateState { copy(selectedNavItem = action.item) }
        }
    }

    private fun initializeDarkMode() {
        viewModelScope.launch {
            localStorage.darkMode
                .distinctUntilChanged()
                .collect {
                    updateState { copy(darkMode = it) }
                }
        }
    }

    private fun initializeStartDestination() {
        viewModelScope.launch {
            localStorage.userId
                .distinctUntilChanged()
                .collect { id ->
                    delay(300)
                    postEffect(NavigateToStartDest(startDestination(userId = id)))
                }
        }
    }
}