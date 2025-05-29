package ru.mareanexx.feature_articles.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class CategoryArticles(
    val category: Category,
    val articles: List<DiscoverArticle>
)

data class DiscoverArticle(
    val id: Int,
    val title: String,
    val content: String,
    val photo: String?,
    @SerializedName(value = "read_time_seconds")
    val readTimeSeconds: Int,
    @SerializedName(value = "created_at")
    val createdAt: OffsetDateTime,
    @SerializedName(value = "profile_id")
    val profileId: Int,
    @SerializedName(value = "is_published")
    val isPublished: Boolean
)