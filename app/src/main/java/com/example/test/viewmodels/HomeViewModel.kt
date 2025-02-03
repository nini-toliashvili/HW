package com.example.test.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.UserRepository
import com.example.test.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository): ViewModel() {
    val usersFlow : StateFlow<List<User>> = repository.getUsers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun refreshUsers() {
        viewModelScope.launch {
            _loading.value = true
            repository.refreshUsers()
            _loading.value = false
        }
    }
}