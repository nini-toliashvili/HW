package com.example.homework23.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun saveUserSession(token: String, rememberMe: Boolean)
    fun getAuthToken(): Flow<String?>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun logout()
    suspend fun saveSession(): Boolean
}