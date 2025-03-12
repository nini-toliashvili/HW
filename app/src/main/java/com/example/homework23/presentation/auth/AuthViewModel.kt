package com.example.homework23.presentation.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework23.data.Resource
import com.example.homework23.domain.inputvalidation.ValidateEmailUseCase
import com.example.homework23.domain.inputvalidation.ValidatePasswordUseCase
import com.example.homework23.domain.usecase.auth.LoginUseCase
import com.example.homework23.domain.usecase.register.RegisterUseCase
import com.example.homework23.domain.usecase.session.IsUserLoggedInUseCase
import com.example.homework23.domain.usecase.session.LogoutUseCase
import com.example.homework23.domain.usecase.session.SaveSessionUseCase
import com.example.homework23.domain.usecase.session.SaveUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val saveUserSessionUseCase: SaveUserSessionUseCase,
    private val isSessionSavedUseCase: SaveSessionUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) :ViewModel() {


    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    init {
        checkUserLoggedIn()
    }

    val loginState: StateFlow<Resource<String>?> = loginUseCase.loginState
    val registerState: StateFlow<Resource<Pair<Int, String>>?> = registerUseCase.registerState



    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {


            val token = loginUseCase(email, password)
            if (!token.isNullOrEmpty()) {
                saveUserSessionUseCase(token, rememberMe)
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerUseCase(email, password)

        }
    }

    fun logOut() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

     private fun checkUserLoggedIn() {
         viewModelScope.launch {
             _isUserLoggedIn.value = isUserLoggedInUseCase()
         }
    }

    suspend fun isSessionSaved() : Boolean {
        return isSessionSavedUseCase()
    }

    fun validateUserInput(email: String, password: String) : Boolean {
        val isEmailValid = validateEmailUseCase(email)
        val isPasswordValid = validatePasswordUseCase(password)
        return isEmailValid && isPasswordValid
    }

}