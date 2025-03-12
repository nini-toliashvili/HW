package com.example.homework23.data.locale.room

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.homework23.data.di.PagingRetrofit
import com.example.homework23.data.remote.api.ApiServiceForPaging
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    @PagingRetrofit private val apiServiceForPaging: ApiServiceForPaging,
    private val database: AppDatabase
) : RemoteMediator<Int, UserEntity>() {


    private val userDao: UserDao = database.userDao()
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, UserEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.id?.div(state.config.pageSize)?.plus(1) ?: 1
            }

        }

        return try {
            val response = apiServiceForPaging.getUsers(page, state.config.pageSize)
            val users = response.data.map { it.toUserEntity() }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.clearAll()
                }
                userDao.insertAll(users)
            }
            MediatorResult.Success(endOfPaginationReached = response.page >= response.totalPages)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}