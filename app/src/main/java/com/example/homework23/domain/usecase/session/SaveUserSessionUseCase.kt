package com.example.homework23.domain.usecase.session

import com.example.homework23.domain.repository.SessionRepository
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(token: String, rememberMe: Boolean) {
        sessionRepository.saveUserSession(token,rememberMe)
    }
}