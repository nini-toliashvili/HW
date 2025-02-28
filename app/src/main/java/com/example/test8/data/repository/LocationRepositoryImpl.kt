package com.example.test8.data.repository

import com.example.test8.data.LocationData
import com.example.test8.data.api.ApiService
import com.example.test8.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val apiService: ApiService) : LocationRepository {
    override suspend fun getLocations() : List<LocationData> {
        return apiService.getLocations()
    }

}