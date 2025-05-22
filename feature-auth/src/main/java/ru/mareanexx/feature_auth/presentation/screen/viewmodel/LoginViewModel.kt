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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.feature_auth.data.mapper.toRequest
import ru.mareanexx.feature_auth.domain.usecase.LoginUseCase
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.event.LoginEvent
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.form.LoginForm
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.state.LoginUiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Init)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _formState = MutableStateFlow(LoginForm())
    val formState: StateFlow<LoginForm> = _formState.asStateFlow()

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(LoginEvent.ShowToast(message ?: "Unknown error"))
        }
    }
    private fun setInit() { _uiState.value = LoginUiState.Init }
    private fun setLoading() { _uiState.value = LoginUiState.Loading }
    private fun setSuccess() { _uiState.value = LoginUiState.Success }
    private fun setError() { _uiState.value = LoginUiState.Error }

    fun onEmailChanged(newValue: String) { _formState.value = _formState.value.copy(email = newValue); setInit() }
    fun onPasswordChanged(newValue: String) { _formState.value = _formState.value.copy(password = newValue); setInit() }

    fun login() {
        viewModelScope.launch {
            loginUseCase(_formState.value.toRequest())
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.localizedMessage); setError() }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> setError()
                        is BaseResult.Success -> setSuccess()
                    }
                }
        }
    }
}