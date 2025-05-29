package ru.mareanexx.feature_articles.data.mapper

import ru.mareanexx.data.category.entity.CategoryArticlesEntity
import ru.mareanexx.data.category.entity.CategoryEntity
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.data.remote.dto.DiscoverArticle

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name
)

fun CategoryEntity.toNetwork() = Category(
    id = id,
    name = name
)

fun DiscoverArticle.toRelations(categoryId: Int) = CategoryArticlesEntity(
    categoryId = categoryId,
    articleId = id
)

fun Category.toRelations(articleId: Int) = CategoryArticlesEntity(
    categoryId = id,
    articleId = articleId
)