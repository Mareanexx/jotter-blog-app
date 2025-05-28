package ru.mareanexx.feature_articles.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_articles.data.remote.dto.CreateArticleRequest
import ru.mareanexx.feature_articles.domain.ArticleRepository
import ru.mareanexx.feature_articles.domain.entity.Article
import java.io.File
import javax.inject.Inject

class CreateArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(newArticle: CreateArticleRequest, photo: File?): Flow<BaseResult<Article, Error>> {
        return articleRepository.create(newArticle, photo)
    }
}