package ru.mareanexx.feature_articles.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.common.network.tracker.NetworkMonitor
import ru.mareanexx.data.category.dao.CategoryDao
import ru.mareanexx.feature_articles.data.mapper.toEntity
import ru.mareanexx.feature_articles.data.mapper.toNetwork
import ru.mareanexx.feature_articles.data.remote.api.CategoryApi
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.domain.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi,
    private val categoryDao: CategoryDao,
    private val networkMonitor: NetworkMonitor
) : CategoryRepository {

    override suspend fun getAll(): Flow<BaseResult<List<Category>, Error>>  = flow {
        if (networkMonitor.isConnected()) {
            fetchCategoriesFromNetwork()
                .flatMapMerge { result ->
                    when (result) {
                        is BaseResult.Success -> flow { emit(result) }
                        is BaseResult.Error -> getCategoriesFromDatabase()
                    }
                }.collect { emit(it) }
        } else { getCategoriesFromDatabase().collect { emit(it) } }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchCategoriesFromNetwork(): Flow<BaseResult<List<Category>, Error>> {
        return try {
            val response = categoryApi.getAll()
            if (response.isSuccessful) {
                flow {
                    val responseData = response.body()!!.data!!
                    categoryDao.refreshTable(responseData.map { it.toEntity() })
                    emit(BaseResult.Success(responseData))
                }
            } else {
                flow { emit(BaseResult.Error(Error(code = response.code()))) }
            }
        } catch (e: Exception) {
            flow { emit(BaseResult.Error(Error(message = e.message))) }
        }
    }

    override suspend fun getCategoriesFromDatabase(): Flow<BaseResult<List<Category>, Error>> {
        return try {
            val response = categoryDao.getAll()
            flow { emit(BaseResult.Success(response.map { it.toNetwork() })) }
        } catch (e: Exception) {
            flow { emit(BaseResult.Error(Error(message = e.message))) }
        }
    }
}