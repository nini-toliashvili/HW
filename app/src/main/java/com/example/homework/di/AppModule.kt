package com.example.homework.di

import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import com.example.homework.data.repository.CameraRepositoryImpl
import com.example.homework.domain.repository.CameraRepository
import com.example.homework.domain.usecase.CaptureImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCameraRepository(context: Context): CameraRepository {
        return CameraRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideCaptureImageUseCase(cameraRepository: CameraRepository): CaptureImageUseCase {
        return CaptureImageUseCase(cameraRepository)
    }
}