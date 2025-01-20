package com.example.myapplication.API

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
    private val BASE_URL = "https://run.mocky.io/v3/9599d49b-35d9-45fe-a8d3-ee3ab8d5d915"


    var retrofit = Retrofit.Builder().
            baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            build()

}