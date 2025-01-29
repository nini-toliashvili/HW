package com.example.homework20.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Support(
    val url: String,
    val text: String
)