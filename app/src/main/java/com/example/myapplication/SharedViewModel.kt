package com.example.myapplication

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class SharedViewModel: ViewModel() {
private val _currentChats = MutableLiveData<List<chatDataDto>>()
    val chats : LiveData<List<chatDataDto>> = _currentChats

    fun loadChatsFromJson(context : Context) {
        val json = context.assets.open("data.json").bufferedReader().use { it.readText() }
        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, chatDataDto::class.java)
        val adapter: JsonAdapter<List<chatDataDto>> = moshi.adapter(listType)
        _currentChats.value =  adapter.fromJson(json) ?: emptyList()
    }

}