package com.example.homework23.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.homework23.data.repository.AuthRepository
import com.example.homework23.data.repository.RoomRepository
import com.example.homework23.data.Resource
import com.example.homework23.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: AuthRepository,
    roomRepository: RoomRepository,
    private val sessionRepository: SessionRepository
) :ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    init {
        isUserLoggedIn()
    }

    val loginState: StateFlow<Resource<String>?> = repository.loginState
    val registerState: StateFlow<Resource<Pair<Int, String>>?> = repository.registerState
    val authToken: StateFlow<String?> = sessionRepository.authToken.stateIn(viewModelScope, SharingStarted.Lazily, null)



    val users = roomRepository.getUsers().cachedIn(viewModelScope)

    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {


            val token = repository.login(email, password)
            if (!token.isNullOrEmpty()) {

                sessionRepository.saveUserSession(token,rememberMe )
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            repository.register(email, password)

        }
    }

    fun logOut() {
        viewModelScope.launch {
            sessionRepository.logout()
        }
    }

     private fun isUserLoggedIn() {
         viewModelScope.launch {
             _isUserLoggedIn.value = sessionRepository.isUserLoggedIn()
         }
    }

    suspend fun isSessionSaved() : Boolean {
        return sessionRepository.saveSession()
    }

}