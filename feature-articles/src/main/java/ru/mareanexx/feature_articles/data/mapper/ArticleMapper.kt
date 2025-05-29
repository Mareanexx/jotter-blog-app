package ru.mareanexx.feature_articles.data.mapper

import ru.mareanexx.data.articles.entity.AuthorArticleEntity
import ru.mareanexx.data.articles.entity.OthersArticleEntity
import ru.mareanexx.feature_articles.data.remote.dto.CreateArticleRequest
import ru.mareanexx.feature_articles.data.remote.dto.DiscoverArticle
import ru.mareanexx.feature_articles.domain.entity.Article
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.form.CreateArticleForm

fun CreateArticleForm.toRequest(markdownContent: String) = CreateArticleRequest(
    title = title,
    content = markdownContent,
    categories = selectedCategories
)

fun Article.toEntity() = AuthorArticleEntity(
    id = id,
    title = title,
    content = content,
    photo = photo,
    readTimeSeconds = readTimeSeconds,
    createdAt = createdAt,
    profileId = profileId,
    isPublished = isPublished
)

fun DiscoverArticle.toEntity() = OthersArticleEntity(
    id = id,
    title = title,
    content = content,
    photo = photo,
    readTimeSeconds = readTimeSeconds,
    createdAt = createdAt,
    profileId = profileId,
    isPublished = isPublished
)