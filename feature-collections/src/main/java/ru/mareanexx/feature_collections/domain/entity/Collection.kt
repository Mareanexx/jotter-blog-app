package ru.mareanexx.feature_collections.domain.entity

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class Collection(
    val id: Int,
    val name: String,
    @SerializedName("number_of_articles")
    val numberOfArticles: Int,
    @SerializedName("created_at")
    val createdAt: OffsetDateTime
)