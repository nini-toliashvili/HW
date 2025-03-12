package com.example.homework23.domain.usecase.session

import com.example.homework23.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthTokenUseCase  @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): Flow<String?> = sessionRepository.getAuthToken()
}