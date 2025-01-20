package com.example.myapplication

import android.content.ClipData
import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter

class items(private val context: Context)  {

    fun loadChatsFromJson() : List<chatDataDto> {
        val json = context.assets.open("data.json").bufferedReader().use { it.readText() }
        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, chatDataDto::class.java)
        val adapter: JsonAdapter<List<chatDataDto>> = moshi.adapter(listType)
        return adapter.fromJson(json) ?: emptyList()
    }
}