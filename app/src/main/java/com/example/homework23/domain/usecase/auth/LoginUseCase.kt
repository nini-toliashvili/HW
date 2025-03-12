package com.example.homework23.domain.usecase.auth

import com.example.homework23.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    val loginState = authRepository.loginState
    suspend operator fun invoke(email:String, password: String) : String? {
        return authRepository.login(email, password)
    }
}