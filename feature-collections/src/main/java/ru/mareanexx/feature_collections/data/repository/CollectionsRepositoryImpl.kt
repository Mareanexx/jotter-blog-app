package ru.mareanexx.feature_collections.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.common.network.tracker.NetworkMonitor
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.articles.dao.OthersArticleDao
import ru.mareanexx.data.collections.dao.CollectionArticlesDao
import ru.mareanexx.data.collections.dao.CollectionsDao
import ru.mareanexx.feature_collections.data.mapper.toCollection
import ru.mareanexx.feature_collections.data.mapper.toCollectionArticle
import ru.mareanexx.feature_collections.data.mapper.toCollectionArticles
import ru.mareanexx.feature_collections.data.mapper.toDbEntity
import ru.mareanexx.feature_collections.data.mapper.toEntity
import ru.mareanexx.feature_collections.data.remote.api.CollectionsApi
import ru.mareanexx.feature_collections.data.remote.dto.CollectionArticle
import ru.mareanexx.feature_collections.data.remote.dto.CollectionWithArticleImages
import ru.mareanexx.feature_collections.data.remote.dto.NewCollectionRequest
import ru.mareanexx.feature_collections.data.remote.dto.UpdateCollectionRequest
import ru.mareanexx.feature_collections.domain.CollectionsRepository
import ru.mareanexx.feature_collections.domain.entity.Collection
import javax.inject.Inject

class CollectionsRepositoryImpl @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val collectionsApi: CollectionsApi,
    private val collectionsDao: CollectionsDao,
    private val othersArticleDao: OthersArticleDao,
    private val collectionArticlesDao: CollectionArticlesDao,
    private val networkMonitor: NetworkMonitor
) : CollectionsRepository {
    override suspend fun getAllCollections(): Flow<BaseResult<List<CollectionWithArticleImages>, Error>> = flow {
        if (networkMonitor.isConnected()) {
            fetchCollectionsFromNetwork()
                .flatMapMerge { result ->
                    when (result) {
                        is BaseResult.Success -> flow { emit(result) }
                        is BaseResult.Error -> getAllFromDatabase()
                    }
                }
                .collect { emit(it) }
        } else {
            getAllFromDatabase().collect { emit(it) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllFromDatabase(): Flow<BaseResult<List<CollectionWithArticleImages>, Error>> {
        return try {
            val collections = collectionsDao.getAll()
            val articles = othersArticleDao.getAll().groupBy { it.collectionId }
            val response = collections.map {
                CollectionWithArticleImages(
                    collection = it.toCollection(),
                    previewPhotos = articles.getOrDefault(it.id, emptyList()).take(3)
                )
            }
            flow { emit(BaseResult.Success(response)) }
        } catch (e: Exception) {
            flow { emit(BaseResult.Error(Error(message = e.message))) }
        }
    }

    override suspend fun fetchCollectionsFromNetwork(): Flow<BaseResult<List<CollectionWithArticleImages>, Error>> {
        return try {
            val response = collectionsApi.getAll()

            if (response.isSuccessful) {
                flow {
                    val responseData = response.body()!!.data!!
                    val profileId = userSessionManager.getProfileId()
                    collectionsDao.insertAll(responseData.map { it.collection.toDbEntity(profileId) })
                    emit(BaseResult.Success(responseData))
                }
            } else {
                flow { emit(BaseResult.Error(Error(code = response.code()))) }
            }
        } catch (e: Exception) {
            flow { emit(BaseResult.Error(Error(message = e.message))) }
        }
    }
    
    override suspend fun getCollectionArticles(collectionId: Int): Flow<BaseResult<List<CollectionArticle>, Error>> = flow {
        if (networkMonitor.isConnected()) {
            fetchArticlesFromNetwork(collectionId).collect { result ->
                when (result) {
                    is BaseResult.Success -> emit(result)
                    is BaseResult.Error -> {
                        getCollectionArticlesFromDatabase(collectionId).collect { dbResult ->
                            emit(dbResult)
                        }
                    }
                }
            }
        } else { getCollectionArticlesFromDatabase(collectionId).collect { dbResult -> emit(dbResult) } }
    }


    override suspend fun getCollectionArticlesFromDatabase(collectionId: Int): Flow<BaseResult<List<CollectionArticle>, Error>> {
        return flow {
            try {
                val result = othersArticleDao.getAllByCollectionId(collectionId).map { it.toCollectionArticle(collectionId) }
                emit(BaseResult.Success(result))
            } catch (e: Exception) {
                emit(BaseResult.Error(Error(message = e.message)))
            }
        }
    }

    override suspend fun fetchArticlesFromNetwork(collectionId: Int): Flow<BaseResult<List<CollectionArticle>, Error>> {
        return flow {
            try {
                val response = collectionsApi.getAllById(collectionId)
                if (response.isSuccessful) {
                    val data = response.body()!!.data!!
                    othersArticleDao.insertAll(data.map { it.toEntity() })
                    collectionArticlesDao.insertAll(data.map { it.toCollectionArticles() })
                    emit(BaseResult.Success(data))
                } else {
                    emit(BaseResult.Error(Error(code = response.code(), message = response.message())))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(BaseResult.Error(Error(message = e.message)))
            }
        }
    }

    override suspend fun add(collectionName: String): Flow<BaseResult<Collection, Error>> {
        return flow {
            val newCollectionRequest = NewCollectionRequest(collectionName)

            val response = collectionsApi.add(newCollectionRequest)

            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                val profileId = userSessionManager.getProfileId()
                collectionsDao.insert(data.toDbEntity(profileId))
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.message())))
            }
        }
    }

    override suspend fun update(updatedCollection: UpdateCollectionRequest): Flow<BaseResult<Collection, Error>> {
        return flow {
            val response = collectionsApi.updateCollectionName(updatedCollection)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                val profileId = userSessionManager.getProfileId()
                collectionsDao.update(data.toDbEntity(profileId))
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.message())))
            }
        }
    }

    override suspend fun deleteById(collectionId: Int): Flow<BaseResult<Unit, Error>> {
        return flow {
            val response = collectionsApi.deleteById(collectionId)
            if (response.isSuccessful) {
                collectionsDao.deleteById(collectionId)
                emit(BaseResult.Success(Unit))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.message())))
            }
        }
    }

    override suspend fun removeArticleFromCollection(collectionId: Int, articleId: Int): Flow<BaseResult<Unit, Error>> {
        return flow {
            val response = collectionsApi.removeArticleFromCollection(collectionId, articleId)
            if (response.isSuccessful) {
                collectionArticlesDao.deleteByIds(collectionId, articleId)
                emit(BaseResult.Success(Unit))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.message())))
            }
        }
    }
}