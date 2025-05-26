package ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.state

sealed class CollectionsUiState {
    data object Loading : CollectionsUiState()
    data object Showing : CollectionsUiState()
    data object Error : CollectionsUiState()
}