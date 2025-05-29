package ru.mareanexx.feature_articles.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_articles.data.remote.dto.CategoryArticles
import ru.mareanexx.feature_articles.domain.ArticleRepository
import javax.inject.Inject

class GetFirstCategoryArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<CategoryArticles>, Error>> {
        return articleRepository.firstGet()
    }
}