package ru.mareanexx.feature_collections.presentation.screens.collection.viewmodel.state

sealed class ConcreteCollectionUiState {
    data object Loading : ConcreteCollectionUiState()
    data object Error : ConcreteCollectionUiState()
    data object Showing : ConcreteCollectionUiState()
}