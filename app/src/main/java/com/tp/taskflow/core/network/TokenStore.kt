package com.tp.taskflow.core.network

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_token",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun read(): String? = prefs.getString(TOKEN, null)

    fun save(token: String) {
        prefs.edit().putString(TOKEN, token).apply()
    }

    fun clear() {
        prefs.edit().remove(TOKEN).apply()
    }

    private companion object {
        const val TOKEN = "auth_token"
    }
}
