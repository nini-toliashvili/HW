package com.example.test7.presentation.viewmodels

import android.media.RouteListingPreference
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test7.data.CardDataModel
import com.example.test7.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repository: Repository) : ViewModel(){
    private val _items = MutableStateFlow<List<CardDataModel>>(emptyList())
    val items: StateFlow<List<CardDataModel>> = _items

    fun fetchItems() {
        viewModelScope.launch {
            try {
                val response = repository.getItems()
                _items.value = response
            } catch (e: Exception) {
                Log.e("ItemViewModel", "Can not fetch data", e)
            }
            }
        }
    }
