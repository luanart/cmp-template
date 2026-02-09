package com.compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.compose.app.ui.app.NavigationContent
import com.compose.app.util.SetStatusBarStyle
import com.core.data.local.LocalStorage
import com.core.presentation.theme.AppTheme
import com.navigation.navigateAsStart
import com.navigation.startDestination
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun App() {
    val local = koinInject<LocalStorage>()
    val navController = rememberNavController()

    val userId by local.userId.collectAsStateWithLifecycle(initialValue = null)
    val darkMode by local.darkMode.collectAsStateWithLifecycle(initialValue = false)

    val isLoaded by produceState(initialValue = false, local.userId) {
        local.userId.collect { value = true }
    }

    LaunchedEffect(isLoaded, userId) {
        if (isLoaded) {
            delay(300)
            navController.navigateAsStart(startDestination(userId))
        }
    }

    AppTheme(isDarkMode = darkMode) {
        SetStatusBarStyle(isDarkMode = darkMode)
        NavigationContent(navController = navController)
    }
}