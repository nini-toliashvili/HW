package com.example.homework.domain.repository

import android.content.Context
import android.net.Uri

interface CameraRepository {
    suspend fun createImageFile(): Uri?
    fun getSavedPhotoUri(): Uri?
    fun updateSharedPreferences(uri: Uri)
}