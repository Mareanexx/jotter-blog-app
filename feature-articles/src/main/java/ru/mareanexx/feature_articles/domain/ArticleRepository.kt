package ru.mareanexx.feature_articles.domain

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.data.remote.dto.CategoryArticles
import ru.mareanexx.feature_articles.data.remote.dto.CreateArticleRequest
import ru.mareanexx.feature_articles.domain.entity.Article
import java.io.File

interface ArticleRepository {
    // get
    suspend fun get(category: Category): Flow<BaseResult<CategoryArticles, Error>>
    suspend fun firstGet(): Flow<BaseResult<List<CategoryArticles>, Error>>

    // create
    suspend fun create(newArticle: CreateArticleRequest, photo: File?): Flow<BaseResult<Article, Error>>
}