package com.example.homework16.api

import kotlinx.serialization.Serializable
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class RegisterRequest(val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class AuthResponse(val success: Boolean, val token: String?)


interface AuthenticationService {
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest) : retrofit2.Response<AuthResponse>

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): retrofit2.Response<AuthResponse>
}