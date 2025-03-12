package com.example.homework23.domain.usecase.session

import com.example.homework23.domain.repository.SessionRepository
import javax.inject.Inject

class SaveSessionUseCase  @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(): Boolean {
        return sessionRepository.saveSession()
    }
}