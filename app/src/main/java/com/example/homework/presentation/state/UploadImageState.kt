package com.example.homework.presentation.state

sealed class UploadImageState {
    data object Idle : UploadImageState()
    data object Uploading : UploadImageState()
    data class Success(val imageUrl: String) : UploadImageState()
    data class Error(val message: String) : UploadImageState()
}