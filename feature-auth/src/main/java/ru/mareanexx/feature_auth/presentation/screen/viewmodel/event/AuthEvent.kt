package ru.mareanexx.feature_auth.presentation.screen.viewmodel.event

sealed class RegistrationEvent {
    data class ShowToast(val message: String) : RegistrationEvent()
    data object ShowDatePicker : RegistrationEvent()
}

sealed class LoginEvent {
    data class ShowToast(val message: String) : LoginEvent()
}