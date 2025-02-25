package com.example.homework24.data.dto

data class PostDto(
    val id: Int,
    val images: List<String>?,
    val title: String,
    val comments: Int,
    val likes: Int,
    val share_content: String,
    val owner: OwnerDto
)
