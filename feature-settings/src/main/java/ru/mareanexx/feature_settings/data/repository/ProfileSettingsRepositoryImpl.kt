package ru.mareanexx.feature_settings.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.feature_settings.data.mapper.toDto
import ru.mareanexx.feature_settings.data.mapper.toEntity
import ru.mareanexx.feature_settings.data.remote.api.ProfileSettingsApi
import ru.mareanexx.feature_settings.data.remote.dto.UpdateProfileRequest
import ru.mareanexx.feature_settings.domain.ProfileSettingsRepository
import ru.mareanexx.feature_settings.domain.entity.Profile
import java.io.File
import javax.inject.Inject

class ProfileSettingsRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val profileSettingsApi: ProfileSettingsApi,
    private val profileDao: ProfileDao,
    private val userSessionManager: UserSessionManager
) : ProfileSettingsRepository {

    override suspend fun getProfile(): Flow<BaseResult<Profile, Error>> {
        return flow {
            val profileEntity = profileDao.get()

            if (profileEntity != null) {
                emit(BaseResult.Success(profileEntity.toDto()))
            } else {
                fetchProfileFromNetwork().collect { result ->
                    when(result) {
                        is BaseResult.Success -> {
                            profileDao.insert(result.data.toEntity())
                            userSessionManager.saveProfileId(result.data.id)
                            emit(BaseResult.Success(result.data))
                        }
                        is BaseResult.Error -> {
                            emit(BaseResult.Error(result.error))
                        }
                    }
                }
            }
        }
    }

    override suspend fun fetchProfileFromNetwork(): Flow<BaseResult<Profile, Error>> {
        return flow {
            val userUuid = userSessionManager.getUserUuid()
            val response = profileSettingsApi.get(userUuid)

            if (response.isSuccessful) {
                val profile = response.body()!!.data!!
                emit(BaseResult.Success(profile))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.body()?.message)))
            }
        }
    }

    override suspend fun updateProfile(updatedProfile: UpdateProfileRequest, avatar: File?): Flow<BaseResult<Profile, Error>> {
        return flow {
            val jsonBody = gson.toJson(updatedProfile).toRequestBody("application/json".toMediaTypeOrNull())

            val avatarPart = avatar?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("avatar", it.name, requestFile)
            }

            val response = profileSettingsApi.update(jsonBody, avatarPart)

            if (response.isSuccessful) {
                val profileResponse = response.body()!!.data!!
                profileDao.update(profileResponse.toEntity())
                emit(BaseResult.Success(profileResponse))
            } else {
                emit(BaseResult.Error(
                    Error(code = response.code(), message = response.body()?.message))
                )
            }
        }
    }
}