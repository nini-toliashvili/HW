package com.example.homework24.domain.usecase

import com.example.homework24.domain.entity.Post
import com.example.homework24.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke() : List<Post> {
        return postRepository.getPosts()
    }
}