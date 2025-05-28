package ru.mareanexx.feature_articles.data.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.mareanexx.common.data.remote.common.WrappedResponse
import ru.mareanexx.feature_articles.domain.entity.Article

interface ArticleApi {
    @Multipart
    @POST("articles")
    suspend fun create(
        @Part("data") data: RequestBody,
        @Part photo: MultipartBody.Part?,
    ) : Response<WrappedResponse<Article>>
}