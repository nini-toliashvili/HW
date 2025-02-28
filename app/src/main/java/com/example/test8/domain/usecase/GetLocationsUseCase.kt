package com.example.test8.domain.usecase

import com.example.test8.data.LocationData
import com.example.test8.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationsUseCase@Inject constructor(private val locationRepository: LocationRepository) {
    suspend fun execute(): List<LocationData> {
        return locationRepository.getLocations()
    }
}