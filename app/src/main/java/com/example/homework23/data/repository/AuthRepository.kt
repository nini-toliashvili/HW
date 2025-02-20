package com.example.homework23.data.repository

import android.util.Log
import com.example.homework23.UserPreferences
import com.example.homework23.data.Resource
import com.example.homework23.data.client.ApiService
import com.example.homework23.data.di.AuthRetrofit
import com.example.homework23.data.loginRequest
import com.example.homework23.data.loginResponse
import com.example.homework23.data.registerRequest
import com.example.homework23.data.registerResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Response
import javax.inject.Inject


class AuthRepository @Inject constructor(
   @AuthRetrofit private val apiService: ApiService,
    val userPreferences: UserPreferences
) {
    private val _loginState = MutableStateFlow<Resource<String>?>(null)
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<Resource<Pair<Int, String>>?>(null)
    val registerState = _registerState.asStateFlow()

    suspend fun login(email: String, password: String) :String? {
        val loginRequest = loginRequest(email, password)
        val response = apiService.login(loginRequest)
        return handleLoginResponse(response)

    }

    suspend fun register(email: String, password: String) : Pair<Int, String>?  {

        val response = apiService.register(registerRequest(email, password))
        return handleRegisterResponse(response)

    }

    private suspend fun handleLoginResponse(response: Response<loginResponse>) : String? {
        return if (response.isSuccessful) {
            val token = response.body()?.token
            if (!token.isNullOrEmpty()) {
                _loginState.update { Resource.Success(token) }
                token
            } else {
                _loginState.update { Resource.Error("Token not found") }
                null
            }
        } else {
            Log.d("Response Code", "Code: ${response.code()}, Message: ${response.message()}")

            _loginState.update { Resource.Error("Authentication failed: ${response.message()}") }
            null
        }
    }

    private suspend fun handleRegisterResponse(response: Response<registerResponse>) : Pair<Int, String>? {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.token.isNotEmpty()) {
                _registerState.update {
                    Resource.Success(
                        Pair(
                            body.id,
                            body.token
                        )
                    )
                }
                Pair(body.id, body.token)
            } else {
                _registerState.update {Resource.Error("Token not found")  }
                null
            }
        } else {
            _registerState.update {Resource.Error("Registration failed: ${response.message()}")  }
            null
        }
    }


}