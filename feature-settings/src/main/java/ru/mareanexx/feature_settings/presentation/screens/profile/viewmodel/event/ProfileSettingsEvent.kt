package ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.event

sealed class ProfileSettingsEvent {
    data class ShowToast(val message: String) : ProfileSettingsEvent()
    data object ShowDatePicker : ProfileSettingsEvent()
}