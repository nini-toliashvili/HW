package com.example.homework23

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.homework23.data.Resource
import com.example.homework23.data.api.ApiService
import com.example.homework23.data.loginRequest
import com.example.homework23.data.loginResponse
import com.example.homework23.data.registerRequest
import com.example.homework23.data.registerResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService,
     val userPreferences: UserPreferences
) {
    private val _loginState = MutableStateFlow<Resource<String>?>(null)
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<Resource<Pair<Int, String>>?>(null)
    val registerState = _registerState.asStateFlow()

    suspend fun login(email: String, password: String) {
        val loginRequest = loginRequest(email, password)
        val response = apiService.login(loginRequest)
        handleLoginResponse(response)

    }

    suspend fun register(email: String, password: String) {

        val response = apiService.register(registerRequest(email, password))
        handleRegisterResponse(response)

    }

    private suspend fun handleLoginResponse(response: Response<loginResponse>) {
        if (response.isSuccessful) {
            val token = response.body()?.token
            if (!token.isNullOrEmpty()) {
                userPreferences.saveAuthToken(token)
                _loginState.update { Resource.Success(token) }
            } else {
                _loginState.update { Resource.Error("Token not found") }
            }
        } else {
            Log.d("Response Code", "Code: ${response.code()}, Message: ${response.message()}")

            _loginState.update { Resource.Error("Authentication failed: ${response.message()}") }
        }
    }

    private suspend fun handleRegisterResponse(response: Response<registerResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.token.isNotEmpty()) {
                userPreferences.saveAuthToken(body.token)
                _registerState.update {
                    Resource.Success(
                        Pair(
                            body.id,
                            body.token
                        )
                    )
                }
            } else {
                _registerState.update {Resource.Error("Token not found")  }
            }
        } else {
            _registerState.update {Resource.Error("Registration failed: ${response.message()}")  }
        }
    }


}