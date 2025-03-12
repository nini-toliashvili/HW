package com.example.homework23.data.remote.api

import com.example.homework23.data.remote.LoginRequest
import com.example.homework23.data.remote.LoginResponse
import com.example.homework23.data.remote.RegisterRequest
import com.example.homework23.data.remote.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest) : Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body request: RegisterRequest) : Response<RegisterResponse>



}