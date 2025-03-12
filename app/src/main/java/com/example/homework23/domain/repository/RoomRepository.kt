package com.example.homework23.domain.repository

import androidx.paging.PagingData
import com.example.homework23.data.locale.room.UserEntity
import com.example.homework23.domain.model.User
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getUsers(): Flow<PagingData<User>>
}