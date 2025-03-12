package com.example.homework23.data.repository

import com.example.homework23.UserPreferences
import com.example.homework23.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(private val userPreferences: UserPreferences)
    : SessionRepository{

    override suspend fun saveUserSession(token: String, rememberMe: Boolean) {
        userPreferences.saveAuthToken(token, rememberMe)
    }
    override fun getAuthToken(): Flow<String?> = userPreferences.authToken


    private val rememberMe: Flow<Boolean> = userPreferences.rememberMe

    override suspend fun isUserLoggedIn(): Boolean {
        val token = userPreferences.authToken.first()
        val rememberMe = userPreferences.rememberMe.first()
        return rememberMe && !token.isNullOrBlank()
    }

    override suspend fun logout() {
         userPreferences.clearAuthToken()
    }

    override suspend fun saveSession():Boolean {
        val rememberMeValue = rememberMe.first()
        return rememberMeValue
    }
}