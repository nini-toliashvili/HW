package com.example.homework.domain.usecase

import android.net.Uri
import com.example.homework.domain.repository.CameraRepository
import javax.inject.Inject

class LoadLastImageUseCase @Inject constructor(
    private val cameraRepository: CameraRepository
) {
    operator fun invoke() : Uri?{
       return cameraRepository.getSavedPhotoUri()
    }
}