package com.example.homework23.data.api

import com.example.homework23.data.loginRequest
import com.example.homework23.data.loginResponse
import com.example.homework23.data.registerRequest
import com.example.homework23.data.registerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: loginRequest) : Response<loginResponse>

    @POST("register")
    suspend fun register(@Body request: registerRequest) : Response<registerResponse>



}