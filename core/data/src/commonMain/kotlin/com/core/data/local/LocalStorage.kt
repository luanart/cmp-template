package com.core.data.local

import kotlinx.coroutines.flow.Flow

interface LocalStorage {
    val userId: Flow<Int?>
    suspend fun storeUserId(userId: Int)
    val darkMode: Flow<Boolean>
    suspend fun setThemeAsDarkMode(darkMode: Boolean)
}