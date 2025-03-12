package com.example.homework23.domain.usecase.register

import com.example.homework23.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase@Inject constructor(
    private val registerRepository: RegisterRepository
) {
    val registerState = registerRepository.registerState
    suspend operator fun invoke(email: String, password: String): Pair<Int, String>? {
        return registerRepository.register(email, password)
    }
}