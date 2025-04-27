package com.polije.sosrobahufactoryapp.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "app_prefs")

class TokenManager(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val TOKEN_KEY = stringPreferencesKey("token_api")
        val ROLE_KEY = stringPreferencesKey("user_role")
    }


    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences: Preferences -> preferences[Keys.TOKEN_KEY] ?: "" }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[Keys.TOKEN_KEY] = token
        }
    }

    suspend fun removeToken() {
        dataStore.edit { preferences ->
            preferences.remove(Keys.TOKEN_KEY)
        }
    }

    suspend fun saveUserRole(role: UserRole) {
        dataStore.edit { prefs ->
            prefs[Keys.ROLE_KEY] = role.name
        }
    }

    // Baca user role sebagai Flow
    fun userRoleFlow(): Flow<UserRole?> = dataStore.data
        .map { prefs ->
            prefs[Keys.ROLE_KEY]?.let { UserRole.valueOf(it) }
        }

    suspend fun removeUserRole() {
        dataStore.edit { preferences ->
            preferences.remove(Keys.ROLE_KEY)
        }
    }


}