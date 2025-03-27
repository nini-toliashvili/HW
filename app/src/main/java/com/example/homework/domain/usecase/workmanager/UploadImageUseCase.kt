package com.example.homework.domain.usecase.workmanager

import android.net.Uri
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.homework.data.workmanager.UploadWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val workManager: WorkManager
) {

    suspend fun execute(imageUri: Uri): Result<String> {
        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInputData(workDataOf("imagePath" to imageUri.toString()))
            .build()

        workManager.enqueue(uploadWorkRequest)
        val workInfo = workManager.getWorkInfoByIdFlow(uploadWorkRequest.id).first{it!!.state.isFinished}

        return if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
            val imageUrl = workInfo.outputData.getString("imageUrl") ?: ""
            Result.success(imageUrl)
        } else {
            Result.failure(Exception("Upload failed"))
        }

    }

}