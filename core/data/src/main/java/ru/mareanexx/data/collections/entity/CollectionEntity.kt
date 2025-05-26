package ru.mareanexx.data.collections.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.mareanexx.data.converters.OffsetDateTimeConverter
import java.time.OffsetDateTime

@Entity(tableName = "collections")
@TypeConverters(OffsetDateTimeConverter::class)
data class CollectionEntity(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo("number_of_articles")
    val numberOfArticles: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: OffsetDateTime,
    @ColumnInfo(name = "profile_id")
    val profileId: Int
)