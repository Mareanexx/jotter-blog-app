package ru.mareanexx.feature_articles.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import ru.mareanexx.common.data.remote.common.WrappedResponse
import ru.mareanexx.feature_articles.data.remote.dto.Category

interface CategoryApi {
    @GET("categories")
    suspend fun getAll() : Response<WrappedResponse<List<Category>>>
}