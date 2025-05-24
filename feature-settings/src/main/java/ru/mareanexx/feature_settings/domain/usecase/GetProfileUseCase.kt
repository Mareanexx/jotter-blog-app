package ru.mareanexx.feature_settings.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_settings.domain.ProfileSettingsRepository
import ru.mareanexx.feature_settings.domain.entity.Profile
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileSettingsRepository: ProfileSettingsRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<Profile, Error>> {
        return profileSettingsRepository.getProfile()
    }
}