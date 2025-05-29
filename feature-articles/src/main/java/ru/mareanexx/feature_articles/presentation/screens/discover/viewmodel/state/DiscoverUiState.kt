package ru.mareanexx.feature_articles.presentation.screens.discover.viewmodel.state

sealed class DiscoverUiState {
    data object Loading : DiscoverUiState()
    data object Error : DiscoverUiState()
    data object Success : DiscoverUiState()
}