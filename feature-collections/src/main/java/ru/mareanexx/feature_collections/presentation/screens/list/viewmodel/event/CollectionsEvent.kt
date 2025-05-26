package ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.event

sealed class CollectionsEvent {
    data class ShowBottomSheet(val type: BottomSheetType) : CollectionsEvent()
    data class ShowToast(val message: String) : CollectionsEvent()
    data object ShowDeleteConfirmationDialog : CollectionsEvent()
}

enum class BottomSheetType {
    None, Create, Update
}