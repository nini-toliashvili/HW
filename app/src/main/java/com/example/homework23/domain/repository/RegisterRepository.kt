package com.example.homework23.domain.repository

import com.example.homework23.data.Resource
import kotlinx.coroutines.flow.StateFlow


interface RegisterRepository {
    val registerState: StateFlow<Resource<Pair<Int, String>>?>
    suspend fun register(email: String, password: String): Pair<Int, String>?
}