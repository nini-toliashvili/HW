package com.example.homework25.domain.repository

import com.example.homework25.common.Resource
import com.example.homework25.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun fetchCategories() : Flow<Resource<List<Category>>>
}