package ru.mareanexx.feature_auth.presentation.screen.viewmodel.state

import androidx.annotation.StringRes
import ru.mareanexx.feature_auth.R

sealed class LoginUiState {
    data object Init : LoginUiState()
    data object Loading : LoginUiState()
    data object Success : LoginUiState()
    data object Error : LoginUiState()
}

sealed class RegisterUiState {
    data object Init : RegisterUiState()
    data object Loading : RegisterUiState()
    data object Success : RegisterUiState()
    data class ValidationError(
        val fieldError: Map<Field, ValidationErrorType>
    ) : RegisterUiState()
}

enum class Field {
    EMAIL, USERNAME, PASSWORD, CONF_PASSWORD
}

enum class ValidationErrorType(@StringRes val message: Int) {
    REPEATED_EMAIL(R.string.error_email_already_registered),
    INVALID_EMAIL(R.string.error_invalid_email),
    USERNAME_TOO_SHORT(R.string.error_username_too_short),
    PASSWORD_TOO_SHORT(R.string.error_password_too_short),
    CONFIRM_PASSWORD_NOT_SAME(R.string.error_confirm_password_not_same)
}