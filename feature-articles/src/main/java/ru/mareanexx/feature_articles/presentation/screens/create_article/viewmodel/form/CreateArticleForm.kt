package ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.form

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.mohamedrejeb.richeditor.model.RichTextState
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.ArticleField
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.ValidationErrorType
import java.io.File

data class CreateArticleForm(
    val title: String = "",
    val content: MutableState<RichTextState> = mutableStateOf(RichTextState()),
    val photo: File? = null,

    // categories
    val categorySearch: String = "",
    val selectedCategories: List<Category> = emptyList(),
    val shownCategories: List<Category> = emptyList(),

    val fieldError: Map<ArticleField, ValidationErrorType> = emptyMap()
)