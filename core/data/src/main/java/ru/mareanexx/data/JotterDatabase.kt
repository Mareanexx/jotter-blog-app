package ru.mareanexx.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.data.profile.entity.ProfileEntity

@Database(
    entities = [ProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class JotterDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}