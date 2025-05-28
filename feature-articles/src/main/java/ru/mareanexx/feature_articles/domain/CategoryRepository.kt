package ru.mareanexx.feature_articles.domain

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_articles.data.remote.dto.Category

interface CategoryRepository {
    suspend fun getAll() : Flow<BaseResult<List<Category>, Error>>
    suspend fun fetchCategoriesFromNetwork() : Flow<BaseResult<List<Category>, Error>>
    suspend fun getCategoriesFromDatabase() : Flow<BaseResult<List<Category>, Error>>
}