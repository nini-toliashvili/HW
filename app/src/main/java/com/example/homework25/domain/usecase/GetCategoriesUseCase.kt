package com.example.homework25.domain.usecase

import com.example.homework25.common.Resource
import com.example.homework25.domain.model.Category
import com.example.homework25.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Category>>> {
        return categoryRepository.fetchCategories()
    }
}