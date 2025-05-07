package com.polije.sosrobahufactoryapp.data.datasource.local

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


private val Context.dataStore by preferencesDataStore(name = "app_prefs")


class SessionManager(
    context: Context
) {
    private val dataStore = context.dataStore

    private object Keys {
        val TOKEN = stringPreferencesKey("token_api")
        val ROLE = stringPreferencesKey("user_role")
        val EXPIRES_AT = stringPreferencesKey("expires_at")

    }

    val sessionFlow: Flow<UserSession> = dataStore.data
        .map { prefs ->
            UserSession(
                token = prefs[Keys.TOKEN],
                role = prefs[Keys.ROLE]?.let { UserRole.valueOf(it) },
                expiresAt = prefs[Keys.EXPIRES_AT]
            )
        }
        .distinctUntilChanged()



    fun isLoggedIn(requiredRole: UserRole): Flow<Boolean> = sessionFlow
        .map { session ->
            val hasToken = session.token?.isNotBlank() == true

            val notExpired = session.expiresAt
                ?.let { expiresAtStr ->
                    try {
                        val instant = Instant.parse(expiresAtStr)
                        val expireLdt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                        expireLdt.isAfter(LocalDateTime.now())
                    } catch (e: Exception) {
                        Log.d("Session", "parse expiresAt error: ${e.message}")
                        false
                    }
                } == true

            hasToken && notExpired && session.role == requiredRole
        }
        .distinctUntilChanged()


    suspend fun saveSession(token: String, role: UserRole, expiresAt: String) {
        dataStore.edit { prefs ->
            prefs[Keys.TOKEN] = token
            prefs[Keys.ROLE] = role.name
            prefs[Keys.EXPIRES_AT] = expiresAt
        }
    }

    suspend fun clearSession() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.TOKEN)
            prefs.remove(Keys.ROLE)
            prefs.remove(Keys.EXPIRES_AT)
        }
    }
}