package com.polije.sosrobahufactoryapp.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

 val Context.dataStore by preferencesDataStore(name = "token")

class TokenManager(private val dataStore : DataStore<Preferences>) {

    private val TOKEN_KEY = stringPreferencesKey("token_api")

    fun getToken() : Flow<String> {
        return dataStore.data.map { preferences: Preferences -> preferences[TOKEN_KEY] ?: "" }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun removeToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

}