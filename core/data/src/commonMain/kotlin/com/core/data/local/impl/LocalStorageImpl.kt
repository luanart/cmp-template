package com.core.data.local.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.core.data.local.LocalStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LocalStorageImpl(private val dataStore: DataStore<Preferences>) : LocalStorage {
    companion object {
        private val USER_ID = intPreferencesKey("user_id")
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    override val userId: Flow<Int?>
        get() = dataStore.data.map { it[USER_ID] }

    override suspend fun storeUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    override val darkMode: Flow<Boolean>
        get() = dataStore.data.map { it[DARK_MODE] == true }

    override suspend fun setThemeAsDarkMode(darkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = darkMode
        }
    }
}