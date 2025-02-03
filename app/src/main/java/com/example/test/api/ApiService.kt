package com.example.test.api

import com.example.test.data.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("f3f41821-7434-471f-9baa-ae3dee984e6d")
    suspend fun getUsers(): Response<ApiResponse>
}