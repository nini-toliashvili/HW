package com.example.homework24.data.remote

import com.example.homework24.data.model.Story
import retrofit2.http.GET

interface StoryApiService {
    @GET("00a18030-a8c7-47c4-b0c5-8bff92a29ebf")
    suspend fun getStories(): List<Story>
}