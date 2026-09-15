package com.tp.taskflow.core.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun read(): String? = dataStore.data.map { it[TOKEN] }.first()

    suspend fun save(token: String) {
        dataStore.edit { it[TOKEN] = token }
    }

    suspend fun clear() {
        dataStore.edit { it.remove(TOKEN) }
    }

    private companion object {
        val TOKEN = stringPreferencesKey("auth_token")
    }
}
