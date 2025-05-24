package ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.feature_settings.data.mapper.toForm
import ru.mareanexx.feature_settings.data.mapper.toRequest
import ru.mareanexx.feature_settings.domain.usecase.GetProfileUseCase
import ru.mareanexx.feature_settings.domain.usecase.UpdateProfileUseCase
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.event.ProfileSettingsEvent
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.form.ProfileForm
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.state.Field
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.state.ProfileSettingsUiState
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.state.ValidationErrorType
import java.io.File
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ProfileSettingsViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileSettingsUiState>(ProfileSettingsUiState.Loading)
    val uiState: StateFlow<ProfileSettingsUiState> = _uiState.asStateFlow()

    private val _profileForm = MutableStateFlow(ProfileForm())
    val profileForm: StateFlow<ProfileForm> = _profileForm.asStateFlow()

    val validationErrors = MutableStateFlow<Map<Field, ValidationErrorType>>(emptyMap())

    private val _eventFlow = MutableSharedFlow<ProfileSettingsEvent>()
    val eventFlow: SharedFlow<ProfileSettingsEvent> = _eventFlow.asSharedFlow()

    init { getProfile() }

    fun retry() = getProfile()

    private fun setLoadingState() { _uiState.value = ProfileSettingsUiState.Loading }
    private fun setShowingState() { _uiState.value = ProfileSettingsUiState.Showing }
    private fun setErrorState() { _uiState.value = ProfileSettingsUiState.Error }
    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(ProfileSettingsEvent.ShowToast(message ?: "Unknown error"))
        }
    }
    fun showDatePickerDialog() { viewModelScope.launch { _eventFlow.emit(ProfileSettingsEvent.ShowDatePicker) } }
    private fun setValidationError(fieldsError: Map<Field, ValidationErrorType>) { validationErrors.value = fieldsError }
    private fun removeValidationError(key: Field) {
        if (validationErrors.value.isNotEmpty()) {
            val fieldErrors: MutableMap<Field, ValidationErrorType> = validationErrors.value.toMutableMap()
            fieldErrors.remove(key)
            setValidationError(fieldErrors)
        }
    }

    fun onEmailChanged(newValue: String) { _profileForm.value = _profileForm.value.copy(email = newValue); removeValidationError(Field.EMAIL) }
    fun onUsernameChanged(newValue: String) { _profileForm.value = _profileForm.value.copy(username = newValue); removeValidationError(Field.USERNAME) }
    fun onBioChanged(newValue: String) { _profileForm.value = _profileForm.value.copy(bio = newValue); removeValidationError(Field.BIO) }
    fun onBirthDateChanged(newValue: Long) {
        val birthDate = Instant.ofEpochMilli(newValue).atZone(ZoneId.systemDefault()).toLocalDate()
        _profileForm.value = _profileForm.value.copy(birthdate = birthDate)
    }
    fun onAvatarSelected(newAvatar: File) {
        _profileForm.value = _profileForm.value.copy(avatarFile = newAvatar)
    }

    private fun validateUsername(): Boolean = _profileForm.value.username.length >= 3
    private fun validateEmail(): Boolean {
        val regex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        return regex.matches(_profileForm.value.email)
    }
    private fun validateBio(): Boolean {
        if (_profileForm.value.bio.isEmpty()) return true
        return _profileForm.value.bio.length >= 3
    }

    private fun validateAll(): Boolean {
        val fieldErrors = mutableMapOf<Field, ValidationErrorType>()
        if (!validateUsername()) fieldErrors[Field.USERNAME] = ValidationErrorType.USERNAME_TOO_SHORT
        if (!validateEmail()) fieldErrors[Field.EMAIL] = ValidationErrorType.INVALID_EMAIL
        if (!validateBio()) fieldErrors[Field.BIO] = ValidationErrorType.BIO_TOO_SHORT

        return if (fieldErrors.isEmpty()) { true } else { setValidationError(fieldErrors); false }
    }

    private fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase()
                .onStart { setLoadingState() }
                .catch { exception ->
                    showToast(exception.localizedMessage)
                    setErrorState()
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> setErrorState()
                        is BaseResult.Success -> {
                            _profileForm.value = baseResult.data.toForm()
                            setShowingState()
                        }
                    }
                }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            setLoadingState()
            if (!validateAll()) return@launch
            updateProfileUseCase(
                updatedProfile =_profileForm.value.toRequest(_profileForm.value.id),
                avatar = _profileForm.value.avatarFile
            )
                .catch { exception ->
                    showToast(exception.localizedMessage)
                    setShowingState()
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> setValidationError(mapOf(Field.EMAIL to ValidationErrorType.REPEATED_EMAIL))
                        is BaseResult.Success -> {
                            _profileForm.value = baseResult.data.toForm()
                            setShowingState()
                        }
                    }
                }
        }
    }
}