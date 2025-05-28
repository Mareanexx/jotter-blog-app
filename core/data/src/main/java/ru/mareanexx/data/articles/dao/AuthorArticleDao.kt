package ru.mareanexx.data.articles.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.mareanexx.data.articles.entity.AuthorArticleEntity

@Dao
interface AuthorArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: AuthorArticleEntity)
}