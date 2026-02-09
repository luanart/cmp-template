package com.compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.compose.app.ui.app.NavigationContent
import com.compose.app.util.LaunchAppRouter
import com.compose.app.util.SetStatusBarStyle
import com.core.data.local.LocalStorage
import com.core.presentation.theme.AppTheme
import org.koin.compose.koinInject

@Composable
fun App() {
    val local = koinInject<LocalStorage>()
    val userId by local.userId.collectAsStateWithLifecycle(initialValue = null)
    val darkMode by local.darkMode.collectAsStateWithLifecycle(initialValue = false)

    val navController = rememberNavController()

    LaunchAppRouter(
        userId = userId,
        navController = navController,
        minimumSplashDurationMs = 300
    )

    AppTheme(isDarkMode = darkMode) {
        SetStatusBarStyle(isDarkMode = darkMode)
        NavigationContent(navController = navController)
    }
}