package ru.mareanexx.feature_settings.domain

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_settings.data.remote.dto.UpdateProfileRequest
import ru.mareanexx.feature_settings.domain.entity.Profile
import java.io.File

interface ProfileSettingsRepository {
    suspend fun getProfile() : Flow<BaseResult<Profile, Error>>
    suspend fun fetchProfileFromNetwork() : Flow<BaseResult<Profile, Error>>
    suspend fun updateProfile(updatedProfile: UpdateProfileRequest, avatar: File?) : Flow<BaseResult<Profile, Error>>
}