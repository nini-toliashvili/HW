package com.example.homework25.presentation.category

sealed class CategoriesEvent {
    data class Search(val query: String) : CategoriesEvent()
}