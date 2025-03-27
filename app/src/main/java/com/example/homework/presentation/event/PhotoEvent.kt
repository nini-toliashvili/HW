package com.example.homework.presentation.event

import android.net.Uri


sealed class PhotoEvent {
    data object CaptureImage : PhotoEvent()
    data object OpenGallery: PhotoEvent()
    data class ImageSelected(val uri: Uri) : PhotoEvent()
}