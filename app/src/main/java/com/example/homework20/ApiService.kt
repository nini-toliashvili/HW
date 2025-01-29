package com.example.homework20

import com.example.homework20.datamodel.DataForPages
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : DataForPages

    companion object {
        private const val BASE_URL = "https://reqres.in/api/"

        fun create() : ApiService{

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()) // Adding Kotlin adapter for Moshi
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}