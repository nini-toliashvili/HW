package com.example.homework24.data.repository

import com.example.homework24.data.model.Story
import com.example.homework24.data.remote.StoryApiService
import com.example.homework24.domain.repository.StoryRepository
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val storyApiService: StoryApiService
) : StoryRepository {
    override suspend fun getStories(): List<Story> {
        return storyApiService.getStories()
    }
}