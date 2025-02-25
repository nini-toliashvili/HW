package com.example.homework24.domain.repository

import com.example.homework24.data.model.Story

interface StoryRepository {
    suspend fun getStories() : List<Story>
}