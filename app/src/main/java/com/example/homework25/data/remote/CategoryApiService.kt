package com.example.homework25.data.remote

import com.example.homework25.data.model.CategoryDto
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApiService {
    @GET("499e0ffd-db69-4955-8d86-86ee60755b9c")
    suspend fun fetchCategories() : Response<List<CategoryDto>>
}