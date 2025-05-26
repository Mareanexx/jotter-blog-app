package ru.mareanexx.feature_collections.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.network.tracker.NetworkMonitor
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.articles.dao.OthersArticleDao
import ru.mareanexx.data.collections.dao.CollectionArticlesDao
import ru.mareanexx.data.collections.dao.CollectionsDao
import ru.mareanexx.feature_collections.data.remote.api.CollectionsApi
import ru.mareanexx.feature_collections.data.repository.CollectionsRepositoryImpl
import ru.mareanexx.feature_collections.domain.CollectionsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CollectionsModule {
    @Singleton
    @Provides
    fun provideCollectionsApi(retrofit: Retrofit) : CollectionsApi {
        return retrofit.create(CollectionsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCollectionsRepository(
        userSessionManager: UserSessionManager,
        collectionsApi: CollectionsApi,
        collectionsDao: CollectionsDao,
        othersArticleDao: OthersArticleDao,
        collectionArticlesDao: CollectionArticlesDao,
        networkMonitor: NetworkMonitor
    ) : CollectionsRepository {
        return CollectionsRepositoryImpl(userSessionManager, collectionsApi, collectionsDao, othersArticleDao, collectionArticlesDao, networkMonitor)
    }
}