package com.example.test.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.UserRepository
import com.example.test.api.ApiService
import com.example.test.data.UserDao

class HomeViewModelFactory(private val userDao: UserDao,
                           private val apiService: ApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(UserRepository(userDao, apiService)) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}