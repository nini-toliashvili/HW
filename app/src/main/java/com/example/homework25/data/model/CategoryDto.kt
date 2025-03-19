package com.example.homework25.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    @SerialName("name_de") val nameDe: String?,
    val createdAt: String,
    @SerialName("bgl_number")val bglNumber: String? = null,
    @SerialName("bgl_variant")val bglVariant: String? = null,
    @SerialName("order_id")val orderId: Int,
    @Contextual val main: Any? = null,
    val children: List<CategoryDto> = emptyList()
)