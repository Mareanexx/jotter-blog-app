package ru.mareanexx.feature_settings.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.feature_settings.data.remote.api.ProfileSettingsApi
import ru.mareanexx.feature_settings.data.repository.ProfileSettingsRepositoryImpl
import ru.mareanexx.feature_settings.domain.ProfileSettingsRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ProfileSettingsModule {
    @Singleton
    @Provides
    fun provideProfileSettingsApi(retrofit: Retrofit): ProfileSettingsApi {
        return retrofit.create(ProfileSettingsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileSettingsRepository(
        gson: Gson,
        profileSettingsApi: ProfileSettingsApi,
        profileDao: ProfileDao,
        userSessionManager: UserSessionManager
    ): ProfileSettingsRepository {
        return ProfileSettingsRepositoryImpl(gson, profileSettingsApi, profileDao, userSessionManager)
    }
}