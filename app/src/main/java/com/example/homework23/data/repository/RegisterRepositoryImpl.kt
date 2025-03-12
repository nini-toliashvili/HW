package com.example.homework23.data.repository

import com.example.homework23.data.Resource
import com.example.homework23.data.di.AuthRetrofit
import com.example.homework23.data.remote.RegisterRequest
import com.example.homework23.data.remote.RegisterResponse
import com.example.homework23.data.remote.ApiHelper
import com.example.homework23.data.remote.api.ApiService
import com.example.homework23.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    @AuthRetrofit private val apiService: ApiService
) : RegisterRepository {
    private val _registerState = MutableStateFlow<Resource<Pair<Int, String>>?>(null)
    override val registerState = _registerState.asStateFlow()
    override suspend fun register(email: String, password: String): Pair<Int, String>? {
        val request = RegisterRequest(email, password)
        val result: Resource<RegisterResponse> = ApiHelper.handleHttpRequest {
            apiService.register(request)
        }

        return when (result) {
            is Resource.Success -> {
                val body = result.data
                _registerState.update { Resource.Success(Pair(body.id, body.token)) }
                Pair(body.id, body.token)
            }
            is Resource.Error -> {
                _registerState.update { Resource.Error(result.message) }
                null
            }
        }
    }
}