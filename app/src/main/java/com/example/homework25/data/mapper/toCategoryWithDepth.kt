package com.example.homework25.data.mapper

import com.example.homework25.data.model.CategoryDto
import com.example.homework25.data.model.CategoryWithDepth

//fun CategoryData.toCategoryWithDepth() = CategoryWithDepth(
//    id = id,
//    name = name,
//    nameDe = nameDe,
//    createdAt = createdAt,
//    bglNumber = bglNumber,
//    bglVariant = bglVariant,
//    orderId = orderId,
//    main = main,
//    depth = 0
//)

fun categoriesWithCalculatedDepth(categories: List<CategoryDto>, currentDepth: Int = 0): List<CategoryWithDepth> {
    val result = mutableListOf<CategoryWithDepth>()

    for (category in categories) {
        // Add the current category with its depth
        result.add(
            CategoryWithDepth(
                category.id,
                category.name,
                category.nameDe ?: category.name,
                category.createdAt,
                category.bglNumber,
                category.bglVariant,
                category.orderId,
                category.main,
                currentDepth
            )
        )

        // Recursively calculate the depth for children
        if (category.children.isNotEmpty()) {
            result.addAll(categoriesWithCalculatedDepth(category.children, currentDepth + 1))
        }
    }

    return result
}


//fun categoriesWithCalculatedDepth(categories: List<CategoryDto>): List<CategoryWithDepth> {
//    val result = mutableListOf<CategoryWithDepth>()
//    val stack = ArrayDeque<Pair<CategoryDto, Int>>() // Stack (category, depth)
//
//    // Push root-level categories
//    categories.forEach { stack.addLast(it to 0) }
//
//    while (stack.isNotEmpty()) {
//        val (category, depth) = stack.removeLast() // Pop category
//
//        // Add category to result
//        result.add(
//            CategoryWithDepth(
//                id = category.id,
//                name = category.name,
//                nameDe = category.nameDe ?: category.name,
//                createdAt = category.createdAt,
//                bglNumber = category.bglNumber,
//                bglVariant = category.bglVariant,
//                orderId = category.orderId,
//                main = category.main,
//                depth = depth
//            )
//        )
//
//        // Push children with increased depth
//        category.children.reversed().forEach { stack.addLast(it to depth + 1) }
//    }
//
//    return result
//}