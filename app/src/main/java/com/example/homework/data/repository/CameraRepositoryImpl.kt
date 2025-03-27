package com.example.homework.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.homework.domain.repository.CameraRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) : CameraRepository {
    private val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    override suspend fun createImageFile(): Uri? {
        val photoFile = withContext(Dispatchers.IO) {
            File.createTempFile(
                "IMG_",
                ".jpg",
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )
        }
        val uri =  FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)
        sharedPreferences.edit().putString("photo_uri", uri.toString()).apply()
        return uri
    }

    override fun getSavedPhotoUri(): Uri? {
        val uriString = sharedPreferences.getString("photo_uri", null)
        return if (uriString != null) Uri.parse(uriString) else null
    }

    override fun updateSharedPreferences(uri: Uri) {
        sharedPreferences.edit().putString("photo_uri", uri.toString()).apply()
    }
}