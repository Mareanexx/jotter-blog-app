package ru.mareanexx.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mareanexx.data.JotterDatabase
import ru.mareanexx.data.articles.dao.AuthorArticleDao
import ru.mareanexx.data.articles.dao.OthersArticleDao
import ru.mareanexx.data.collections.dao.CollectionArticlesDao
import ru.mareanexx.data.collections.dao.CollectionsDao
import ru.mareanexx.data.profile.dao.ProfileDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): JotterDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = JotterDatabase::class.java,
            name = "jotter_db"
        ).build()
    }

    @Provides
    fun provideProfileDao(db: JotterDatabase): ProfileDao {
        return db.profileDao()
    }

    @Provides
    fun provideAuthorArticlesDao(db: JotterDatabase): AuthorArticleDao {
        return db.authorArticleDao()
    }

    @Provides
    fun provideOthersArticlesDao(db: JotterDatabase): OthersArticleDao {
        return db.othersArticleDao()
    }

    @Provides
    fun provideCollectionsDao(db: JotterDatabase): CollectionsDao {
        return db.collectionsDao()
    }

    @Provides
    fun provideCollectionArticlesDao(db: JotterDatabase): CollectionArticlesDao {
        return db.collectionArticlesDao()
    }
}