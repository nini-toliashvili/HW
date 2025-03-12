package com.example.homework23.domain.inputvalidation

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean {
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{4,}\$".toRegex()
        return passwordRegex.matches(password)
    }
}