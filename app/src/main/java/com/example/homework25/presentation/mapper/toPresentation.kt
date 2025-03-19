package com.example.homework25.presentation.mapper

import com.example.homework25.domain.model.Category
import com.example.homework25.presentation.model.CategoryUI

fun Category.toPresentation() = CategoryUI(
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