package com.example.homework23.domain.inputvalidation

class ValidateEmailUseCase {
    operator fun invoke(email: String): Boolean {
        val emailRegex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex()
        return emailRegex.matches(email)
    }
}