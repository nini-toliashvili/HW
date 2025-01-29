package com.example.homework20.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataForPages(
    val page: Int,
    @Json(name = "per_page") val perPage: Int,
    val total: Int,
    @Json(name = "total_pages") val totalPages: Int,
    val data: List<User>,
    val support: Support
)