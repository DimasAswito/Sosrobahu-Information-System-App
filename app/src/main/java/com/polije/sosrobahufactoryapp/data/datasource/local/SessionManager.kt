package com.polije.sosrobahufactoryapp.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "app_prefs")


class SessionManager(
    context: Context
) {
    private val dataStore = context.dataStore

    private object Keys {
        val TOKEN = stringPreferencesKey("token_api")
        val ROLE = stringPreferencesKey("user_role")
    }

    /**
     * Emits the current user session whenever DataStore changes.
     */
    val sessionFlow: Flow<UserSession> = dataStore.data
        .map { prefs ->
            UserSession(
                token = prefs[Keys.TOKEN],
                role = prefs[Keys.ROLE]?.let { UserRole.valueOf(it) }
            )
        }
        .distinctUntilChanged()

    val isLoggedIn: Flow<Boolean> = sessionFlow
        .map { it.token?.isNotBlank() == true }
        .distinctUntilChanged()

    /**
     * Save token and role in one transaction.
     */
    suspend fun saveSession(token: String, role: UserRole) {
        dataStore.edit { prefs ->
            prefs[Keys.TOKEN] = token
            prefs[Keys.ROLE] = role.name
        }
    }

    suspend fun clearSession() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.TOKEN)
            prefs.remove(Keys.ROLE)
        }
    }
}