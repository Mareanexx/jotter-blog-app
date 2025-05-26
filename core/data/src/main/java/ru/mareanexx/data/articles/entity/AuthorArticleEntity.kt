package ru.mareanexx.data.articles.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.mareanexx.data.collections.entity.CollectionEntity
import ru.mareanexx.data.converters.OffsetDateTimeConverter
import ru.mareanexx.data.profile.entity.ProfileEntity
import java.time.OffsetDateTime

@Entity(
    tableName = "articles",
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            childColumns = ["profile_id"],
            parentColumns = ["id"],
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CollectionEntity::class,
            childColumns = ["collection_id"],
            parentColumns = ["id"],
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["profile_id"]), Index(value = ["collection_id"])]
)
@TypeConverters(OffsetDateTimeConverter::class)
data class AuthorArticleEntity(
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
    @ColumnInfo(name = "collection_id")
    val collectionId: Int? = null,
    @ColumnInfo(name = "is_published")
    val isPublished: Boolean
)