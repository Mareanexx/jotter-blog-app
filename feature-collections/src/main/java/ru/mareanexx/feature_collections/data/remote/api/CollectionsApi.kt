package ru.mareanexx.feature_collections.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mareanexx.common.data.remote.common.WrappedResponse
import ru.mareanexx.feature_collections.data.remote.dto.CollectionArticle
import ru.mareanexx.feature_collections.data.remote.dto.CollectionWithArticleImages
import ru.mareanexx.feature_collections.data.remote.dto.NewCollectionRequest
import ru.mareanexx.feature_collections.data.remote.dto.UpdateCollectionRequest
import ru.mareanexx.feature_collections.domain.entity.Collection

interface CollectionsApi {
    @POST("collections")
    suspend fun add(@Body newCollection: NewCollectionRequest) : Response<WrappedResponse<Collection>>

    @GET("collections")
    suspend fun getAll() : Response<WrappedResponse<List<CollectionWithArticleImages>>>

    @GET("collections/{collectionId}")
    suspend fun getAllById(@Path ("collectionId") collectionId: Int) : Response<WrappedResponse<List<CollectionArticle>>>

    @DELETE("collections/{collectionId}")
    suspend fun deleteById(@Path ("collectionId") collectionId: Int) : Response<WrappedResponse<Unit>>

    @DELETE("collections")
    suspend fun removeArticleFromCollection(
        @Query ("collection_id") collectionId: Int,
        @Query ("article_id") articleId: Int
    ) : Response<WrappedResponse<Unit>>

    @PATCH("collections")
    suspend fun updateCollectionName(@Body updated: UpdateCollectionRequest) : Response<WrappedResponse<Collection>>
}