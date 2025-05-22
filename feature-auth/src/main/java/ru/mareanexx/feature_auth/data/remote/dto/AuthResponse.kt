package ru.mareanexx.feature_auth.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class AuthResponse(
    @SerializedName("user_uuid") val userUuid: UUID,
    @SerializedName("access_token") val accessToken: String
)