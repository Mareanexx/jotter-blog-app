package ru.mareanexx.feature_articles.data.repository

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
import ru.mareanexx.feature_articles.data.mapper.toEntity
import ru.mareanexx.feature_articles.data.remote.api.ArticleApi
import ru.mareanexx.feature_articles.data.remote.dto.CreateArticleRequest
import ru.mareanexx.feature_articles.domain.ArticleRepository
import ru.mareanexx.feature_articles.domain.entity.Article
import java.io.File
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val articleApi: ArticleApi,
    private val authorArticleDao: AuthorArticleDao
): ArticleRepository {

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
                emit(BaseResult.Success(article))
            } else {
                emit(BaseResult.Error(
                    Error(code = response.code(), message = response.body()?.message))
                )
            }
        }
    }
}