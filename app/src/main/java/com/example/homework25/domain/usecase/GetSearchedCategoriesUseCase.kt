package com.example.homework25.domain.usecase

import android.util.Log
import com.example.homework25.common.Resource
import com.example.homework25.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetSearchedCategoriesUseCase @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) {
    suspend operator fun invoke(query: String) : Flow<Resource<List<Category>>> {
        return getCategoriesUseCase()
            .transform { resource ->
                when (resource) {
                    is Resource.Loader -> emit(Resource.Loader(true))
                    is Resource.Success -> {
                        val filteredCategories = resource.data.filter { category ->
                            category.name.contains(query, ignoreCase = true)

                        }
                        emit(Resource.Success(filteredCategories))
                    }
                    is Resource.Error -> {
                        Log.d("usecasesearch", "error" )
                        Resource.Error(resource.message)}
                }
            }
    }
}