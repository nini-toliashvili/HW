package com.example.test8.data.api

import com.example.test8.data.LocationData
import retrofit2.http.GET

interface ApiService {
    @GET("c4c64996-4ed9-4cbc-8986-43c4990d495a")
    suspend fun getLocations() : List<LocationData>
}