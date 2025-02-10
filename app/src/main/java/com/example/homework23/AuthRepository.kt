package com.example.homework23

import androidx.lifecycle.MutableLiveData
import com.example.homework23.data.Resource
import com.example.homework23.data.api.ApiService
import com.example.homework23.data.handleHttpRequest
import com.example.homework23.data.loginRequest
import com.example.homework23.data.loginResponse
import com.example.homework23.data.registerRequest
import com.example.homework23.data.registerResponse
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {
    val loginState = MutableLiveData<Resource<String>>()
    val registerState = MutableLiveData<Resource<Pair<Int, String>>>()

    suspend fun login(email: String, password: String) {

        val response = apiService.login(loginRequest(email, password))
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
                loginState.postValue(Resource.Success(token))
            } else {
                loginState.postValue(Resource.Error("Token not found"))
            }
        } else {
            loginState.postValue(Resource.Error("Authentication failed: ${response.message()}"))
        }
    }

    private suspend fun handleRegisterResponse(response: Response<registerResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.token.isNotEmpty()) {
                userPreferences.saveAuthToken(body.token)
                registerState.postValue(
                    Resource.Success(
                        Pair(
                            body.id,
                            body.token
                        )
                    )
                )  // Success state with ID and token
            } else {
                registerState.postValue(Resource.Error("Token not found"))  // Error if token is missing
            }
        } else {
            registerState.postValue(Resource.Error("Registration failed: ${response.message()}"))  // Error if request fails
        }
    }


}