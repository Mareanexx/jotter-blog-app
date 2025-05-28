package ru.mareanexx.data.category.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.mareanexx.data.category.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("DELETE FROM category")
    suspend fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Transaction
    suspend fun refreshTable(items: List<CategoryEntity>) {
        clearTable()
        insertAll(items)
    }

    @Query("SELECT * FROM category")
    suspend fun getAll(): List<CategoryEntity>
}