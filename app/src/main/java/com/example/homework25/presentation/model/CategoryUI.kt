package com.example.homework25.presentation.model

data class CategoryUI (
    val id: String,
    val name: String,
    val nameDe: String,
    val createdAt: String,
    val bglNumber: String? = null,
    val bglVariant: String? = null,
    val orderId: Int,
    val main: Any? = null,
    val depth: Int
)