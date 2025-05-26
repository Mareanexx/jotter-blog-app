package ru.mareanexx.feature_collections.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_collections.domain.CollectionsRepository
import javax.inject.Inject

class RemoveArticleFromCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(collectionId: Int, articleId: Int): Flow<BaseResult<Unit, Error>> {
        return collectionsRepository.removeArticleFromCollection(collectionId = collectionId, articleId = articleId)
    }
}