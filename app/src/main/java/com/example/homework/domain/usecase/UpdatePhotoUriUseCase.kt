package com.example.homework.domain.usecase

import android.net.Uri
import com.example.homework.domain.repository.CameraRepository
import javax.inject.Inject

class UpdatePhotoUriUseCase@Inject constructor(
    private val cameraRepository: CameraRepository
) {
    operator fun invoke(uri: Uri) {
        cameraRepository.updateSharedPreferences(uri = uri)
    }
}