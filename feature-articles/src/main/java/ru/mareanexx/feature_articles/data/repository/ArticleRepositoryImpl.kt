package ru.mareanexx.feature_articles.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.data.articles.dao.AuthorArticleDao
import ru.mareanexx.data.articles.dao.OthersArticleDao
import ru.mareanexx.data.category.dao.CategoryArticlesDao
import ru.mareanexx.data.category.dao.CategoryDao
import ru.mareanexx.feature_articles.data.mapper.toEntity
import ru.mareanexx.feature_articles.data.mapper.toRelations
import ru.mareanexx.feature_articles.data.remote.api.ArticleApi
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.data.remote.dto.CategoryArticles
import ru.mareanexx.feature_articles.data.remote.dto.CreateArticleRequest
import ru.mareanexx.feature_articles.domain.ArticleRepository
import ru.mareanexx.feature_articles.domain.entity.Article
import java.io.File
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val articleApi: ArticleApi,
    private val authorArticleDao: AuthorArticleDao,
    private val othersArticleDao: OthersArticleDao,
    private val categoryDao: CategoryDao,
    private val categoryArticlesDao: CategoryArticlesDao
): ArticleRepository {

    override suspend fun get(category: Category): Flow<BaseResult<CategoryArticles, Error>> {
        return flow {
            val response = articleApi.getCategoryArticles(category.id)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                othersArticleDao.insertAll(data.articles.map { it.toEntity() })
                Log.d("1GET_CATEGORY", "Вот это мы спокойно проходим")
                categoryArticlesDao.insertAll(entities = data.run { this.articles.map { it.toRelations(this.category.id) } })
                Log.d("2GET_CATEGORY", "Вот это мы спокойно проходим")
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.message())))
            }
        }
    }

    override suspend fun firstGet(): Flow<BaseResult<List<CategoryArticles>, Error>> {
        return flow {
            val response = articleApi.getFirst()
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                categoryDao.refreshTable(data.map { it.category.toEntity() })
                Log.d("FIRST_GET_CATEGORY", "После рефреша в таблице категорий есть такие данные '${categoryDao.getAll()}'")
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.message())))
            }
        }
    }

    override suspend fun create(newArticle: CreateArticleRequest, photo: File?): Flow<BaseResult<Article, Error>> {
        return flow {
            val jsonBody = gson.toJson(newArticle).toRequestBody("application/json".toMediaTypeOrNull())

            val avatarPart = photo?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("photo", it.name, requestFile)
            }

            val response = articleApi.create(jsonBody, avatarPart)

            if (response.isSuccessful) {
                val article = response.body()!!.data!!
                authorArticleDao.insert(article.toEntity())
                categoryDao.insertAll(article.categories.map { it.toEntity() })
                categoryArticlesDao.insertAll(
                    entities = article.categories.map { it.toRelations(article.id) }
                )

                emit(BaseResult.Success(article))
            } else {
                emit(BaseResult.Error(
                    Error(code = response.code(), message = response.body()?.message))
                )
            }
        }
    }
}