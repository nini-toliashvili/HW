package com.example.homework23.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.homework23.AuthRepository
import com.example.homework23.RoomRepository
import com.example.homework23.UserPreferences
import com.example.homework23.data.Resource
import com.example.homework23.data.api.ApiClient
import com.example.homework23.data.pagination.ApiServiceForPaging
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository(ApiClient.apiService, UserPreferences(application))

    val loginState: StateFlow<Resource<String>?> = repository.loginState
    val registerState: StateFlow<Resource<Pair<Int, String>>?> = repository.registerState
    val authToken: StateFlow<String?> = repository.userPreferences.authToken.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val roomRepository = RoomRepository(ApiServiceForPaging.create(), application.applicationContext)

    val users = roomRepository.getUsers().cachedIn(viewModelScope)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch { repository.register(email, password) }
    }

    fun logOut() {
        viewModelScope.launch {
            repository.userPreferences.clearAuthToken()
        }
    }
}