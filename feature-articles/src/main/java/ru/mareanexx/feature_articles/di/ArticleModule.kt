package ru.mareanexx.feature_articles.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.network.tracker.NetworkMonitor
import ru.mareanexx.data.articles.dao.AuthorArticleDao
import ru.mareanexx.data.articles.dao.OthersArticleDao
import ru.mareanexx.data.category.dao.CategoryArticlesDao
import ru.mareanexx.data.category.dao.CategoryDao
import ru.mareanexx.feature_articles.data.remote.api.ArticleApi
import ru.mareanexx.feature_articles.data.remote.api.CategoryApi
import ru.mareanexx.feature_articles.data.repository.ArticleRepositoryImpl
import ru.mareanexx.feature_articles.data.repository.CategoryRepositoryImpl
import ru.mareanexx.feature_articles.domain.ArticleRepository
import ru.mareanexx.feature_articles.domain.CategoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticleModule {
    @Singleton
    @Provides
    fun provideArticleRepository(
        gson: Gson,
        articleApi: ArticleApi,
        authorArticleDao: AuthorArticleDao,
        othersArticleDao: OthersArticleDao,
        categoryDao: CategoryDao,
        categoryArticlesDao: CategoryArticlesDao
    ): ArticleRepository {
        return ArticleRepositoryImpl(
            gson, articleApi, authorArticleDao,
            othersArticleDao = othersArticleDao,
            categoryDao = categoryDao,
            categoryArticlesDao = categoryArticlesDao
        )
    }

    @Singleton
    @Provides
    fun provideArticleApi(retrofit: Retrofit): ArticleApi {
        return retrofit.create(ArticleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        categoryApi: CategoryApi,
        categoryDao: CategoryDao,
        networkMonitor: NetworkMonitor
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryApi, categoryDao, networkMonitor)
    }

    @Singleton
    @Provides
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }
}