package ru.mareanexx.feature_settings.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_settings.data.remote.dto.UpdateProfileRequest
import ru.mareanexx.feature_settings.domain.entity.Profile
import ru.mareanexx.feature_settings.domain.ProfileSettingsRepository
import java.io.File
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileSettingsRepository: ProfileSettingsRepository
) {
    suspend operator fun invoke(updatedProfile: UpdateProfileRequest, avatar: File?): Flow<BaseResult<Profile, Error>> {
        return profileSettingsRepository.updateProfile(updatedProfile, avatar)
    }
}