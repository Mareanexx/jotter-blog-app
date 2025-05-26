package ru.mareanexx.feature_collections.domain

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_collections.data.remote.dto.CollectionArticle
import ru.mareanexx.feature_collections.data.remote.dto.CollectionWithArticleImages
import ru.mareanexx.feature_collections.data.remote.dto.UpdateCollectionRequest
import ru.mareanexx.feature_collections.domain.entity.Collection

interface CollectionsRepository {
    // get
    suspend fun getAllCollections() : Flow<BaseResult<List<CollectionWithArticleImages>, Error>>
    suspend fun getCollectionArticles(collectionId: Int) : Flow<BaseResult<List<CollectionArticle>, Error>>
    suspend fun getAllFromDatabase() : Flow<BaseResult<List<CollectionWithArticleImages>, Error>>
    suspend fun getCollectionArticlesFromDatabase(collectionId: Int) : Flow<BaseResult<List<CollectionArticle>, Error>>
    suspend fun fetchCollectionsFromNetwork() : Flow<BaseResult<List<CollectionWithArticleImages>, Error>>
    suspend fun fetchArticlesFromNetwork(collectionId: Int) : Flow<BaseResult<List<CollectionArticle>, Error>>
    // add
    suspend fun add(collectionName: String) : Flow<BaseResult<Collection, Error>>
    // update
    suspend fun update(updatedCollection: UpdateCollectionRequest) : Flow<BaseResult<Collection, Error>>
    // delete
    suspend fun deleteById(collectionId: Int) : Flow<BaseResult<Unit, Error>>
    suspend fun removeArticleFromCollection(collectionId: Int, articleId: Int) : Flow<BaseResult<Unit, Error>>
}