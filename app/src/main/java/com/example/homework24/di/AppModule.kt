package com.example.homework24.di

import com.example.homework24.data.remote.PostApiService
import com.example.homework24.data.remote.StoryApiService
import com.example.homework24.data.repository.PostRepositoryImpl
import com.example.homework24.data.repository.StoryRepositoryImpl
import com.example.homework24.domain.repository.PostRepository
import com.example.homework24.domain.repository.StoryRepository
import com.example.homework24.domain.usecase.GetPostsUseCase
import com.example.homework24.domain.usecase.GetStoriesUseCase
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
    fun provideStoryApiService(retrofit: Retrofit) : StoryApiService {
        return retrofit.create(StoryApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(storyApiService: StoryApiService) : StoryRepository {
        return StoryRepositoryImpl(storyApiService)
    }

    @Provides
    @Singleton
    fun provideGetStoriesUseCase(storyRepository: StoryRepository) : GetStoriesUseCase {
        return GetStoriesUseCase(storyRepository)
    }



    @Provides
    @Singleton
    fun providePostApiService(retrofit: Retrofit) : PostApiService {
        return retrofit.create(PostApiService::class.java)
    }


    @Provides
    @Singleton
    fun providePostRepository(postApiService: PostApiService) : PostRepository {
        return PostRepositoryImpl(postApiService)
    }

    @Provides
    @Singleton
    fun provideGetPostsUseCase(postRepository: PostRepository) : GetPostsUseCase {
        return GetPostsUseCase(postRepository)
    }

}