package com.example.test8.domain.repository

import com.example.test8.data.LocationData
import com.example.test8.data.api.ApiService

interface LocationRepository {
    suspend fun getLocations() : List<LocationData>
}