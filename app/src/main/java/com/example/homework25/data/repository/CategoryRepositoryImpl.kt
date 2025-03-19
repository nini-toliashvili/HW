package com.example.homework25.data.repository

import com.example.homework25.common.ApiHelper
import com.example.homework25.common.Resource
import com.example.homework25.common.mapResource
import com.example.homework25.data.mapper.categoriesWithCalculatedDepth
import com.example.homework25.data.mapper.toDomain
import com.example.homework25.data.remote.CategoryApiService
import com.example.homework25.domain.model.Category
import com.example.homework25.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: CategoryApiService,
    private val apiHelper: ApiHelper,
) :
    CategoryRepository {
    override suspend fun fetchCategories(): Flow<Resource<List<Category>>> {
        return apiHelper.handleHttpRequest {
            apiService.fetchCategories()
        } . mapResource { dtoCategories ->
            categoriesWithCalculatedDepth(dtoCategories).map { it.toDomain() }
        }
    }



}