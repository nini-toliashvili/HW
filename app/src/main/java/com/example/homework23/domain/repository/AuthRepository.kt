package com.example.homework23.domain.repository

import com.example.homework23.data.Resource
import kotlinx.coroutines.flow.StateFlow


interface AuthRepository {
    val loginState: StateFlow<Resource<String>?>
    suspend fun login(email: String, password: String): String?
}