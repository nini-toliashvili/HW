package com.example.homework.presentation.state

import android.net.Uri

data class GalleryState(
    val imageUri: Uri?,
    val error: String?
)