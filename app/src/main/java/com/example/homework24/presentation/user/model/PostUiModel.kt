package com.example.homework24.presentation.user.model

data class PostUiModel(
    val id: Int,
    val images: List<String>,
    val title: String,
    val comments: String,
    val likes: String,
    val ownerFullName: String,
    val ownerProfile: String?,
    val formattedDate: String
)