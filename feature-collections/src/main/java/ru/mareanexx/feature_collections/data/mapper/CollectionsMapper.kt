package ru.mareanexx.feature_collections.data.mapper

import ru.mareanexx.data.articles.entity.OthersArticleEntity
import ru.mareanexx.data.collections.entity.CollectionArticlesEntity
import ru.mareanexx.data.collections.entity.CollectionEntity
import ru.mareanexx.feature_collections.data.remote.dto.CollectionArticle
import ru.mareanexx.feature_collections.domain.entity.Collection

fun CollectionEntity.toCollection() = Collection(
    id = id,
    name = name,
    numberOfArticles = numberOfArticles,
    createdAt = createdAt
)

fun Collection.toDbEntity(profileId: Int) = CollectionEntity(
    id = id,
    name = name,
    numberOfArticles = numberOfArticles,
    createdAt = createdAt,
    profileId = profileId
)

fun CollectionArticle.toEntity() = OthersArticleEntity(
    id = id,
    title = title,
    content = content,
    photo = photo,
    readTimeSeconds = readTimeSeconds,
    createdAt = createdAt,
    profileId = profileId,
    isPublished = true
)

fun OthersArticleEntity.toCollectionArticle(collectionId: Int) = CollectionArticle(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    photo = photo,
    readTimeSeconds = readTimeSeconds,
    profileId = profileId,
    collectionId = collectionId
)

fun CollectionArticle.toCollectionArticles() = CollectionArticlesEntity(
    collectionId = collectionId,
    articleId = id
)