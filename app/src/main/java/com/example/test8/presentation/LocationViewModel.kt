package com.example.test8.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test8.data.LocationData
import com.example.test8.domain.usecase.GetLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val getLocationsUseCase: GetLocationsUseCase) : ViewModel() {
    private val _locations = MutableLiveData<List<LocationData>>()
    val locations: LiveData<List<LocationData>> = _locations

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchLocations() {
        viewModelScope.launch {
            try {
                val locationList = getLocationsUseCase.execute()
                _locations.postValue(locationList)
            } catch (e: Exception) {
                _error.postValue("Failed to fetch locations")
            }
        }
    }

}