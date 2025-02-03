package com.example.test

import android.util.Log
import com.example.test.api.ApiService
import com.example.test.data.User
import com.example.test.data.UserDao
import com.google.protobuf.api
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao, private val apiService: ApiService) {

    fun getUsers(): Flow<List<User>> = userDao.getAll()

    suspend fun refreshUsers() {
        try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    Log.d("UserRepository", "Fetched users: ${apiResponse.users}")
                    userDao.clearUsers()
                    userDao.insertAll(apiResponse.users)
                }
            } else  Log.e("UserRepository", "API Error: ${response.errorBody()}")
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception: ${e.message}")
            e.printStackTrace()
        }
    }
}
