package com.example.homework23.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homework23.data.di.PagingRetrofit
import com.example.homework23.data.pagination.ApiServiceForPaging
import com.example.homework23.data.room.AppDatabase
import com.example.homework23.data.room.UserEntity
import com.example.homework23.data.room.UserRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    @PagingRetrofit private val apiServiceForPaging: ApiServiceForPaging,
    private val database: AppDatabase
) {


    @OptIn(ExperimentalPagingApi::class)
    fun getUsers(): Flow<PagingData<UserEntity>> {
        return Pager(config = PagingConfig(
            pageSize = 6, // Matches API response
            enablePlaceholders = false
        ),
            remoteMediator = UserRemoteMediator(apiServiceForPaging, database),
            pagingSourceFactory = {database.userDao().getUsers() }
        ).flow
    }
}