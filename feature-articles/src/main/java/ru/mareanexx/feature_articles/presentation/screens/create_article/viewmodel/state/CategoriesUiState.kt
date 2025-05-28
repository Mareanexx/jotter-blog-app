package ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state

import androidx.annotation.StringRes
import ru.mareanexx.feature_articles.R

sealed class CategoriesUiState {
    data object LoadingCategories : CategoriesUiState()
    data object ErrorLoadingCategories : CategoriesUiState()
    data object Showing : CategoriesUiState()
}

sealed class CreateArticleUiState {
    data object ProcessingCreation : CreateArticleUiState()
    data object ErrorCreatingArticle : CreateArticleUiState()
    data object Showing : CreateArticleUiState()
}

enum class ArticleField {
    Name, Categories, Content
}

enum class ValidationErrorType(@StringRes val errorText: Int) {
    TOO_SHORT_NAME(R.string.error_article_name_too_short),
    NO_CATEGORIES(R.string.error_no_categories_selected),
    NO_CONTENT(R.string.error_article_content)
}