package ru.mareanexx.feature_auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_auth.data.remote.api.AuthApi
import ru.mareanexx.feature_auth.data.repository.AuthRepositoryImpl
import ru.mareanexx.feature_auth.domain.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        userSessionManager: ru.mareanexx.common.utils.UserSessionManager,
        authApi: AuthApi
    ): AuthRepository {
        return AuthRepositoryImpl(userSessionManager, authApi)
    }
}