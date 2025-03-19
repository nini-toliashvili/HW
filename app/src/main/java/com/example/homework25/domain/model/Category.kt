package com.example.homework25.domain.model

data class Category(
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