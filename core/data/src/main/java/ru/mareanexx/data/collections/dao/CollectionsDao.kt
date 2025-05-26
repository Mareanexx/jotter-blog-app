package ru.mareanexx.data.collections.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.mareanexx.data.collections.entity.CollectionEntity

@Dao
interface CollectionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: CollectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collections: List<CollectionEntity>)

    @Query("SELECT * FROM collections")
    suspend fun getAll(): List<CollectionEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(collection: CollectionEntity)

    @Query("DELETE FROM collections WHERE id = :collectionId")
    suspend fun deleteById(collectionId: Int)
}