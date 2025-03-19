package com.example.homework25.data.mapper

import com.example.homework25.data.model.CategoryWithDepth
import com.example.homework25.domain.model.Category

fun CategoryWithDepth.toDomain() = Category(
    id = id,
    name = name,
    nameDe = nameDe,
    createdAt = createdAt,
    bglNumber = bglNumber,
    bglVariant = bglVariant,
    orderId = orderId,
    main = main,
    depth = depth
)