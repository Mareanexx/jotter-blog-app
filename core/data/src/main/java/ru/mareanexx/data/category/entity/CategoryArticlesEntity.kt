package ru.mareanexx.data.category.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "category_articles",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["category_id", "article_id"],
    indices = [Index(value = ["category_id"])]
)
data class CategoryArticlesEntity(
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "article_id")
    val articleId: Int
)