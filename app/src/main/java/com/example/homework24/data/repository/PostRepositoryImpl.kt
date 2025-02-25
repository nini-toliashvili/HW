package com.example.homework24.data.repository

import com.example.homework24.data.datamapper.toDomain
import com.example.homework24.data.remote.PostApiService
import com.example.homework24.domain.entity.Post
import com.example.homework24.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: PostApiService
) :PostRepository {
    override suspend fun getPosts(): List<Post> {
        return apiService.getPosts().map { it.toDomain() } }
    }


