package com.example.homework23.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.homework23.data.di.PagingRetrofit
import com.example.homework23.data.remote.api.ApiServiceForPaging
import com.example.homework23.data.locale.room.AppDatabase
import com.example.homework23.data.locale.room.UserEntity
import com.example.homework23.data.locale.room.UserRemoteMediator
import com.example.homework23.domain.model.User
import com.example.homework23.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    @PagingRetrofit private val apiServiceForPaging: ApiServiceForPaging,
    private val database: AppDatabase
) : RoomRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(): Flow<PagingData<User>> {
        return Pager(config = PagingConfig(
            pageSize = 6,
            enablePlaceholders = false
        ),
            remoteMediator = UserRemoteMediator(apiServiceForPaging, database),
            pagingSourceFactory = {database.userDao().getUsers() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }
}