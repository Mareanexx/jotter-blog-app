package ru.mareanexx.data.articles.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.mareanexx.data.converters.OffsetDateTimeConverter
import java.time.OffsetDateTime

@Entity(tableName = "others_articles")
@TypeConverters(OffsetDateTimeConverter::class)
data class OthersArticleEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
    val photo: String?,
    @ColumnInfo(name = "read_time_seconds")
    val readTimeSeconds: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: OffsetDateTime,
    @ColumnInfo(name = "profile_id")
    val profileId: Int,
    @ColumnInfo(name = "is_published")
    val isPublished: Boolean
)