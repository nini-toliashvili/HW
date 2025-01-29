package com.example.homework20.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework20.ApiService
import com.example.homework20.PagingSource
import com.example.homework20.datamodel.User
import kotlinx.coroutines.flow.Flow

class MainViewModel (private val apiService: ApiService) : ViewModel() {
    val users : Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 2
        ),
        pagingSourceFactory = { PagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)
}