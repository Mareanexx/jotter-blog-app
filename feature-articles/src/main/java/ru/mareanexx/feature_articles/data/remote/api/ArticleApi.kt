package ru.mareanexx.feature_articles.data.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import ru.mareanexx.common.data.remote.common.WrappedResponse
import ru.mareanexx.feature_articles.data.remote.dto.CategoryArticles
import ru.mareanexx.feature_articles.domain.entity.Article

interface ArticleApi {
    @Multipart
    @POST("articles")
    suspend fun create(
        @Part("data") data: RequestBody,
        @Part photo: MultipartBody.Part?,
    ) : Response<WrappedResponse<Article>>

    @GET("articles/first-category")
    suspend fun getFirst(): Response<WrappedResponse<List<CategoryArticles>>>

    @GET("articles")
    suspend fun getCategoryArticles(
        @Query("category_id") categoryId: Int
    ): Response<WrappedResponse<CategoryArticles>>
}