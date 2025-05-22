package ru.mareanexx.feature_auth.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.feature_auth.data.mapper.toRequest
import ru.mareanexx.feature_auth.domain.usecase.RegisterUseCase
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.event.RegistrationEvent
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.form.RegisterForm
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.state.Field
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.state.RegisterUiState
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.state.ValidationErrorType
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Init)
    val uiState : StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<RegistrationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _formState = MutableStateFlow(RegisterForm())
    val formState = _formState.asStateFlow()

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(RegistrationEvent.ShowToast(message ?: "Unknown error"))
        }
    }
    fun showDatePickerDialog() { viewModelScope.launch { _eventFlow.emit(RegistrationEvent.ShowDatePicker) } }
    private fun removeValidationError(key: Field) {
        if (_uiState.value is RegisterUiState.ValidationError) {
            val fieldErrors: MutableMap<Field, ValidationErrorType> = (uiState.value as? RegisterUiState.ValidationError)?.fieldError?.toMutableMap() ?: mutableMapOf()
            fieldErrors.remove(key)
            setValidationError(fieldErrors)
            if (fieldErrors.isEmpty()) setInit()
        }
    }
    private fun setInit() { _uiState.value = RegisterUiState.Init }
    private fun setLoading() { _uiState.value = RegisterUiState.Loading }
    private fun setSuccess() { _uiState.value = RegisterUiState.Success }
    private fun setValidationError(fieldsError: Map<Field, ValidationErrorType>) { _uiState.value = RegisterUiState.ValidationError(fieldsError) }

    fun onEmailChanged(newValue: String) { _formState.value = _formState.value.copy(email = newValue); removeValidationError(Field.EMAIL) }
    fun onUsernameChanged(newValue: String) { _formState.value = _formState.value.copy(username = newValue); removeValidationError(Field.USERNAME) }
    fun onPasswordChanged(newValue: String) { _formState.value = _formState.value.copy(password = newValue); removeValidationError(Field.PASSWORD) }
    fun onConfirmPasswordChanged(newValue: String) { _formState.value = _formState.value.copy(confirmPassword = newValue); removeValidationError(Field.CONF_PASSWORD) }
    fun onBirthDateChanged(newValue: Long) {
        val birthDate = Instant.ofEpochMilli(newValue).atZone(ZoneId.systemDefault()).toLocalDate()
        _formState.value = _formState.value.copy(birthdate = birthDate)
    }

    private fun validateUsername(): Boolean = _formState.value.username.length >= 3
    private fun validatePassword(): Boolean = _formState.value.password.length >= 6
    private fun validateConfirmPassword(): Boolean = _formState.value.password == _formState.value.confirmPassword
    private fun validateEmail(): Boolean {
        val regex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        return regex.matches(_formState.value.email)
    }

    private fun validateAll(): Boolean {
        val fieldErrors = mutableMapOf<Field, ValidationErrorType>()
        if (!validateUsername()) fieldErrors[Field.USERNAME] = ValidationErrorType.USERNAME_TOO_SHORT
        if (!validatePassword()) fieldErrors[Field.PASSWORD] = ValidationErrorType.PASSWORD_TOO_SHORT
        if (!validateEmail()) fieldErrors[Field.EMAIL] = ValidationErrorType.INVALID_EMAIL
        if (!validateConfirmPassword()) fieldErrors[Field.CONF_PASSWORD] = ValidationErrorType.CONFIRM_PASSWORD_NOT_SAME

        return if (fieldErrors.isEmpty()) { true } else { setValidationError(fieldErrors); false }
    }

    fun register() {
        viewModelScope.launch {
            setLoading()
            if (!validateAll()) return@launch
            registerUseCase(_formState.value.toRequest())
                .catch { exception -> showToast(exception.localizedMessage) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> setValidationError(mapOf(Field.EMAIL to ValidationErrorType.REPEATED_EMAIL))
                        is BaseResult.Success -> setSuccess()
                    }
                }
        }
    }
}