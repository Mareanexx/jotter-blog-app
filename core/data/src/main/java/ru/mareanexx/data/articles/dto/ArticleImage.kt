package ru.mareanexx.data.articles.dto

import androidx.room.ColumnInfo

data class ArticleImage(
    val id: Int,
    val photo: String,
    @ColumnInfo(name = "collection_id")
    val collectionId: Int
)