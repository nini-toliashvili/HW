package com.example.homework23.data.pagination

import com.example.homework20.datamodel.DataForPages
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceForPaging {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : DataForPages



}