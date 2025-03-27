package com.example.homework.presentation.state

import android.net.Uri

sealed class CameraState {
    data object Idle : CameraState()
    data class Captured(val uri: Uri) : CameraState()
    data class DisplayLast(val uri: Uri) : CameraState()
    data class Error(val message: String) : CameraState()
}