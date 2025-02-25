package com.example.homework24.domain.usecase

import com.example.homework24.data.model.Story
import com.example.homework24.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) {
    suspend operator fun invoke() : List<Story> {
        return storyRepository.getStories()
    }
}