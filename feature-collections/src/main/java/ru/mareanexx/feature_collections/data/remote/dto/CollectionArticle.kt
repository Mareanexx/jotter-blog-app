package ru.mareanexx.feature_collections.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class CollectionArticle(
    val id: Int,
    val title: String,
    val content: String,
    @SerializedName(value = "created_at")
    val createdAt: OffsetDateTime,
    val photo: String?,
    @SerializedName(value = "read_time_seconds")
    val readTimeSeconds: Int,
    @SerializedName(value = "profile_id")
    val profileId: Int,
    @SerializedName(value = "collection_id")
    val collectionId: Int
)