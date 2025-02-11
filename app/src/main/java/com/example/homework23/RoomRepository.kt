package com.example.homework23

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homework23.data.pagination.ApiServiceForPaging
import com.example.homework23.data.room.AppDatabase
import com.example.homework23.data.room.UserDao
import com.example.homework23.data.room.UserEntity
import com.example.homework23.data.room.UserRemoteMediator
import kotlinx.coroutines.flow.Flow

class RoomRepository(
    private val apiServiceForPaging: ApiServiceForPaging,
    private val context: Context
) {


    private val database: AppDatabase = AppDatabase.getDatabase(context)
    @OptIn(ExperimentalPagingApi::class)
    fun getUsers(): Flow<PagingData<UserEntity>> {
        return Pager(config = PagingConfig(
            pageSize = 6, // Matches API response
            enablePlaceholders = false
        ),
            remoteMediator = UserRemoteMediator(apiServiceForPaging, context),
            pagingSourceFactory = {database.userDao().getUsers() }
        ).flow
    }
}