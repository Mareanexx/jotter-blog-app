package ru.mareanexx.feature_settings.data.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Query
import ru.mareanexx.common.data.remote.common.WrappedResponse
import ru.mareanexx.feature_settings.domain.entity.Profile
import java.util.UUID

interface ProfileSettingsApi {
    @GET("profile")
    suspend fun get(@Query("userUuid") userUuid: UUID) : Response<WrappedResponse<Profile>>

    @Multipart
    @PATCH("profile")
    suspend fun update(
        @Part("data") data: RequestBody,
        @Part avatar: MultipartBody.Part?,
    ) : Response<WrappedResponse<Profile>>
}