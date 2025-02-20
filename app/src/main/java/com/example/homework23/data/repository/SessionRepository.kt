package com.example.homework23.data.repository

import com.example.homework23.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(private val userPreferences: UserPreferences) {

    suspend fun saveUserSession(token: String, rememberMe: Boolean) {
        userPreferences.saveAuthToken(token, rememberMe)
    }

    val authToken: Flow<String?> = userPreferences.authToken
    private val rememberMe: Flow<Boolean> = userPreferences.rememberMe

    suspend fun isUserLoggedIn(): Boolean {
        val token = userPreferences.authToken.first()
        val rememberMe = userPreferences.rememberMe.first()
        return rememberMe && !token.isNullOrBlank()
    }

    suspend fun logout() {
         userPreferences.clearAuthToken()
    }

    suspend fun saveSession():Boolean {
        val rememberMeValue = rememberMe.first()
        return rememberMeValue
    }
}