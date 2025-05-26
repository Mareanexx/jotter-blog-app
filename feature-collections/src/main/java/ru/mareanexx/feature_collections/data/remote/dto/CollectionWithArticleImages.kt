package ru.mareanexx.feature_collections.data.remote.dto

import ru.mareanexx.data.articles.dto.ArticleImage
import ru.mareanexx.feature_collections.domain.entity.Collection

data class CollectionWithArticleImages(
    val collection: Collection,
    val previewPhotos: List<ArticleImage>
)