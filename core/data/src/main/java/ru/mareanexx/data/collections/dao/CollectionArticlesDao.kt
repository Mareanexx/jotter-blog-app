package ru.mareanexx.data.collections.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mareanexx.data.collections.entity.CollectionArticlesEntity

@Dao
interface CollectionArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CollectionArticlesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectionArticle: CollectionArticlesEntity)

    @Query("DELETE FROM collection_articles WHERE collection_id = :collectionId AND article_id = :articleId")
    suspend fun deleteByIds(collectionId: Int, articleId: Int)
}