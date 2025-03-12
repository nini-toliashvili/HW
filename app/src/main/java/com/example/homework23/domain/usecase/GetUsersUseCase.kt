package com.example.homework23.domain.usecase

import androidx.paging.PagingData
import com.example.homework23.data.locale.room.UserEntity
import com.example.homework23.domain.model.User
import com.example.homework23.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: RoomRepository
) {
    operator fun invoke(): Flow<PagingData<User>> {
        return repository.getUsers()
    }
}