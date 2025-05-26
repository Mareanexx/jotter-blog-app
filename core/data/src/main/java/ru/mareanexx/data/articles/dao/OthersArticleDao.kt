package ru.mareanexx.data.articles.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mareanexx.data.articles.dto.ArticleImage
import ru.mareanexx.data.articles.entity.OthersArticleEntity

@Dao
interface OthersArticleDao {
    @Query("SELECT id, photo, ca.collection_id FROM articles a JOIN collection_articles ca ON ca.article_id = a.id")
    suspend fun getAll() : List<ArticleImage>

    @Query("SELECT a.* FROM articles a JOIN collection_articles ca ON ca.article_id = a.id WHERE ca.collection_id = :collectionId")
    suspend fun getAllByCollectionId(collectionId: Int) : List<OthersArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(othersArticles: List<OthersArticleEntity>)
}