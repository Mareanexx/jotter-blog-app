package ru.mareanexx.feature_settings.domain.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

data class Profile(
    val id: Int,
    val email: String,
    val username: String,
    val birthdate: LocalDate?,
    val bio: String?,
    val avatar: String?,
    @SerializedName("user_uuid")
    val userUuid: UUID,
    @SerializedName("created_at")
    val createdAt: OffsetDateTime
)