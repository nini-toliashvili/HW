package com.example.homework23

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework23.data.api.ApiClient
import kotlinx.coroutines.launch

class ViewModel(application : Application) : AndroidViewModel(application) {
    private val repository = AuthRepository(ApiClient.apiService, UserPreferences(application))


    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch { repository.register(email, password) }
    }
}