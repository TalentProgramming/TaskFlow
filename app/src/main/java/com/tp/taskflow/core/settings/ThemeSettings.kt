package com.tp.taskflow.core.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeSettings @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val darkMode: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[DARK_MODE] == true
    }

    suspend fun setDark(enabled: Boolean) {
        dataStore.edit { it[DARK_MODE] = enabled }
    }

    private companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}
