package com.example.homework.data.workmanager

import android.content.Context
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import javax.inject.Inject

class UploadWorker @Inject constructor(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams){
    override fun doWork(): Result {
        val imagePath = inputData.getString("imagePath") ?: return Result.failure()

        val file = File(imagePath)
        if (!file.exists()) return Result.failure()

        return try {
            val storage = FirebaseStorage.getInstance()
            val storageRef : StorageReference = storage.reference
            val imageRef = storageRef.child("images/${file.name}")

            val uploadTask = imageRef.putFile(file.toUri())

            uploadTask.addOnSuccessListener {
                Result.success()
            }.addOnFailureListener{
                Result.failure()
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }


}