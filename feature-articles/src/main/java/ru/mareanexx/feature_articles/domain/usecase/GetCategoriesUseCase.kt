package ru.mareanexx.feature_articles.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.domain.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<Category>, Error>> {
        return categoryRepository.getAll()
    }
}