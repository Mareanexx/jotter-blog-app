package ru.mareanexx.feature_articles.data.remote.dto

data class CreateArticleRequest(
    val title: String,
    val content: String,
    val categories: List<Category>
)