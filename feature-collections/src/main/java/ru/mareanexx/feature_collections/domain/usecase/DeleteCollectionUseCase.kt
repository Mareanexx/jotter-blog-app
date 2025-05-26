package ru.mareanexx.feature_collections.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_collections.domain.CollectionsRepository
import javax.inject.Inject

class DeleteCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(deletedCollectionId: Int): Flow<BaseResult<Unit, Error>> {
        return collectionsRepository.deleteById(deletedCollectionId)
    }
}