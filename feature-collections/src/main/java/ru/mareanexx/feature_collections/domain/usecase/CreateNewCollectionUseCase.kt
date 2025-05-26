package ru.mareanexx.feature_collections.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_collections.domain.CollectionsRepository
import ru.mareanexx.feature_collections.domain.entity.Collection
import javax.inject.Inject

class CreateNewCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(collectionName: String): Flow<BaseResult<Collection, Error>> {
        return collectionsRepository.add(collectionName)
    }
}