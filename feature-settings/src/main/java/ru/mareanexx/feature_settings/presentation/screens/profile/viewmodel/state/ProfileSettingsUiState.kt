package ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.state

import androidx.annotation.StringRes
import ru.mareanexx.feature_settings.R

sealed class ProfileSettingsUiState {
    data object Loading : ProfileSettingsUiState()
    data object Showing : ProfileSettingsUiState()
    data object Error : ProfileSettingsUiState()
}

enum class Field {
    EMAIL, USERNAME, BIO
}

enum class ValidationErrorType(@StringRes val message: Int) {
    REPEATED_EMAIL(R.string.error_email_already_registered),
    INVALID_EMAIL(R.string.error_invalid_email),
    USERNAME_TOO_SHORT(R.string.error_username_too_short),
    BIO_TOO_SHORT(R.string.error_bio_too_short)
}