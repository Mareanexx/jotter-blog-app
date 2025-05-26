package ru.mareanexx.common.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mareanexx.common.network.AuthInterceptor
import ru.mareanexx.common.network.adapters.LocalDateAdapter
import ru.mareanexx.common.network.adapters.OffsetDateTimeAdapter
import ru.mareanexx.common.network.tracker.NetworkMonitor
import ru.mareanexx.common.network.tracker.NetworkMonitorImpl
import ru.mareanexx.common.utils.BuildConfig
import ru.mareanexx.common.utils.UserSessionManager
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttp)
            baseUrl(BuildConfig.API_BASE_URL)
        }.build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeAdapter())
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(authInterceptor)
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideAuthInterceptor(userSessionManager: UserSessionManager) : AuthInterceptor {
        return AuthInterceptor(userSessionManager)
    }

    @Provides
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor = NetworkMonitorImpl(context)
}