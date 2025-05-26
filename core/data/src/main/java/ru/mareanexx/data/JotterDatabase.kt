package ru.mareanexx.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mareanexx.data.articles.dao.AuthorArticleDao
import ru.mareanexx.data.articles.dao.OthersArticleDao
import ru.mareanexx.data.articles.entity.AuthorArticleEntity
import ru.mareanexx.data.articles.entity.OthersArticleEntity
import ru.mareanexx.data.collections.dao.CollectionArticlesDao
import ru.mareanexx.data.collections.dao.CollectionsDao
import ru.mareanexx.data.collections.entity.CollectionArticlesEntity
import ru.mareanexx.data.collections.entity.CollectionEntity
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.data.profile.entity.ProfileEntity

@Database(
    entities = [
        ProfileEntity::class,
        AuthorArticleEntity::class,
        CollectionEntity::class,
        OthersArticleEntity::class,
        CollectionArticlesEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class JotterDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun authorArticleDao(): AuthorArticleDao
    abstract fun othersArticleDao(): OthersArticleDao
    abstract fun collectionsDao(): CollectionsDao
    abstract fun collectionArticlesDao(): CollectionArticlesDao
}