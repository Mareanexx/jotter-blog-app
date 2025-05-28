package ru.mareanexx.data.category.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import ru.mareanexx.data.articles.entity.AuthorArticleEntity

@Entity(
    tableName = "category_articles",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"]
        ),
        ForeignKey(
            entity = AuthorArticleEntity::class,
            parentColumns = ["id"],
            childColumns = ["article_id"]
        )
    ],
    primaryKeys = ["category_id", "article_id"],
    indices = [Index(value = ["category_id"]), Index(value = ["article_id"])]
)
data class CategoryArticlesEntity(
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "article_id")
    val articleId: Int
)