package com.example.test8.data.di

import com.example.test8.data.api.ApiService
import com.example.test8.data.repository.LocationRepositoryImpl
import com.example.test8.domain.repository.LocationRepository
import com.example.test8.domain.usecase.GetLocationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStoryApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(apiService: ApiService): LocationRepository {
        return LocationRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetLocationsUseCase(locationRepository: LocationRepository): GetLocationsUseCase {
        return GetLocationsUseCase(locationRepository)
    }
}