package com.example.homework23

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
    }

    suspend fun saveAuthToken(token: String, rememberMe: Boolean) {
        dataStore.edit {
            it[TOKEN_KEY] = token
            it[REMEMBER_ME_KEY] = rememberMe
        }
    }

    val authToken: Flow<String?> = dataStore.data.map { it[TOKEN_KEY] }
    val rememberMe: Flow<Boolean> = dataStore.data.map { it[REMEMBER_ME_KEY] ?: false }

    suspend fun clearAuthToken() {
        dataStore.edit {
            it.remove(TOKEN_KEY)
            it.remove(REMEMBER_ME_KEY)
        }
    }
}