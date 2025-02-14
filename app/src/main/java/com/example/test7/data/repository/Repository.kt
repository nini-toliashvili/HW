package com.example.test7.data.repository

import com.example.test7.data.CardDataModel
import com.example.test7.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getItems(): List<CardDataModel> {
        return apiService.fetchData()
    }
}