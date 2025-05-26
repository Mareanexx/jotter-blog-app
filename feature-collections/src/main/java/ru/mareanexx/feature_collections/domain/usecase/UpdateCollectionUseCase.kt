package ru.mareanexx.feature_collections.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_collections.data.remote.dto.UpdateCollectionRequest
import ru.mareanexx.feature_collections.domain.CollectionsRepository
import ru.mareanexx.feature_collections.domain.entity.Collection
import javax.inject.Inject

class UpdateCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(updatedCollection: UpdateCollectionRequest): Flow<BaseResult<Collection, Error>> {
        return collectionsRepository.update(updatedCollection)
    }
}