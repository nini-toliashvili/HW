package com.example.homework24.data.remote

import com.example.homework24.data.dto.PostDto
import retrofit2.http.GET

interface PostApiService {
    @GET("1ba8b612-8391-41e5-8560-98e4a48decc7")
    suspend fun getPosts(): List<PostDto>
}