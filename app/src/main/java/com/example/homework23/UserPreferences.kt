package com.example.homework23

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)
class UserPreferences(context: Context) {
private  val dataStore = context.dataStore
    companion object {private  val TOKEN_KEY = stringPreferencesKey("auth_token") }

    suspend fun saveAuthToken(token :String) {
        dataStore.edit { it[TOKEN_KEY] = token }
    }

    val authToken : Flow<String?> = dataStore.data.map { it[TOKEN_KEY] }

    suspend fun clearAuthToken() {
        dataStore.edit { it.remove(TOKEN_KEY) }
    }
}