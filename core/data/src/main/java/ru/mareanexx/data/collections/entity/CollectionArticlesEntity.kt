package ru.mareanexx.data.collections.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import ru.mareanexx.data.articles.entity.OthersArticleEntity

@Entity(
    tableName = "collection_articles",
    foreignKeys = [
        ForeignKey(
            entity = OthersArticleEntity::class,
            childColumns = ["article_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CollectionEntity::class,
            childColumns = ["collection_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["article_id"]), Index(value = ["collection_id"])],
    primaryKeys = ["collection_id", "article_id"]
)
data class CollectionArticlesEntity(
    @ColumnInfo(name = "collection_id")
    val collectionId: Int,
    @ColumnInfo(name = "article_id")
    val articleId: Int
)