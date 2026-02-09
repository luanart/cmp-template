package com.compose.app.ui.app

import androidx.lifecycle.viewModelScope
import com.core.data.local.LocalStorage
import com.core.presentation.base.BaseViewModel
import com.navigation.startDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val localStorage: LocalStorage
) : BaseViewModel<NavState, NavAction, NavEffect>(
    initialState = NavState()
) {
    override fun handleAction(action: NavAction) {
        when(action) {
            is NavAction.NavigateTo -> {
                postEffect(effect = NavEffect.NavigateToNavItem(action.route))
            }
            is NavAction.UpdateSelectedNavItem -> {
                updateState { copy(selectedNavItem = action.item) }
            }
        }
    }

    override fun loadInitialData() {
        initializeDarkMode()
        initializeStartDestination()
    }

    private fun initializeDarkMode() {
        viewModelScope.launch {
            localStorage.darkMode
                .distinctUntilChanged()
                .collect { updateState { copy(darkMode = it) } }
        }
    }

    private fun initializeStartDestination() {
        viewModelScope.launch {
            localStorage.userId
                .distinctUntilChanged()
                .collect { id ->
                    delay(300)
                    postEffect(NavEffect.NavigateToStartDest(startDestination(userId = id)))
                }
        }
    }
}