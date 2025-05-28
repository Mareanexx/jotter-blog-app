package ru.mareanexx.feature_articles.data.mapper

import ru.mareanexx.data.category.entity.CategoryEntity
import ru.mareanexx.feature_articles.data.remote.dto.Category

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name
)

fun CategoryEntity.toNetwork() = Category(
    id = id,
    name = name
)