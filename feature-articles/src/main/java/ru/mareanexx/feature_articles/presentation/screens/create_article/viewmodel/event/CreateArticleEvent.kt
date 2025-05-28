package ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.event

sealed class CreateArticleEvent {
    data class ShowToast(val message: String) : CreateArticleEvent()
    data object NextPage : CreateArticleEvent()
    data object ShowSuccessDialog : CreateArticleEvent()
}