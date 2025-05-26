package ru.mareanexx.feature_collections.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_collections.data.remote.dto.CollectionArticle
import ru.mareanexx.feature_collections.domain.CollectionsRepository
import javax.inject.Inject

class GetCollectionArticlesUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(collectionId: Int): Flow<BaseResult<List<CollectionArticle>, Error>> {
        return collectionsRepository.getCollectionArticles(collectionId)
    }
}