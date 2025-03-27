package com.example.homework.presentation.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.domain.usecase.CaptureImageUseCase
import com.example.homework.domain.usecase.LoadLastImageUseCase
import com.example.homework.domain.usecase.UpdatePhotoUriUseCase
import com.example.homework.domain.usecase.workmanager.UploadImageUseCase
import com.example.homework.presentation.event.PhotoEvent
import com.example.homework.presentation.state.CameraState
import com.example.homework.presentation.state.GalleryState
import com.example.homework.presentation.state.UploadImageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val captureImageUseCase: CaptureImageUseCase,
    private val loadLastImageUseCase: LoadLastImageUseCase,
    private val updatePhotoUriUseCase: UpdatePhotoUriUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel(){
    private val _state = MutableStateFlow<CameraState>(CameraState.Idle)
    val state: StateFlow<CameraState> = _state.asStateFlow()


    private val _galleryState = MutableStateFlow(GalleryState(null, "" ))
    val galleryState: StateFlow<GalleryState> get() = _galleryState

    private val _uploadState = MutableStateFlow<UploadImageState>(UploadImageState.Idle)
    val uploadState : StateFlow<UploadImageState> get() = _uploadState


    fun uploadImage(imageUri : Uri) {
        _uploadState.value = UploadImageState.Uploading

        viewModelScope.launch {
            val result = uploadImageUseCase.execute(imageUri)
            _uploadState.value = if (result.isSuccess) {
                UploadImageState.Success(result.getOrNull() ?: "")
            } else {
                UploadImageState.Error("Upload failed")
            }
        }
    }



    fun onEvent(event: PhotoEvent) {
        when (event) {
            is PhotoEvent.CaptureImage -> {captureImage()}
            is PhotoEvent.OpenGallery -> {}
            is PhotoEvent.ImageSelected -> {_galleryState.value = _galleryState.value.copy(imageUri = event.uri)
                updatePhotoUriUseCase(uri = event.uri)}
        }
    }
    init {
//        loadLastImageUseCase()?.let { uri ->
//            _state.value = CameraState.DisplayLast(uri)
//        }
    }

    private fun captureImage() {
        viewModelScope.launch {
            val uri = captureImageUseCase()
            if (uri != null) {
                _state.value = CameraState.Captured(uri)
                updatePhotoUriUseCase(uri = uri)
            } else {
                _state.value = CameraState.Error("Failed tu create image file")
            }

        }
    }



}