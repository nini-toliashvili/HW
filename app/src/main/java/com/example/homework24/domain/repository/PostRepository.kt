package com.example.homework24.domain.repository

import com.example.homework24.domain.entity.Post

interface PostRepository {
    suspend fun getPosts() : List<Post>
}