package com.example.homework25.presentation.category


import com.example.homework25.domain.model.Category

sealed class CategoriesState {
    data object Idle : CategoriesState()
    data object Loading : CategoriesState()
    data class Success(val categories : List<Category>) : CategoriesState()
    data class Error(val message: String) : CategoriesState()
    data object NoMatchesFound : CategoriesState()
}