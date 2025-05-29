package ru.mareanexx.data.category.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.mareanexx.data.category.entity.CategoryArticlesEntity

@Dao
interface CategoryArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<CategoryArticlesEntity>)
}