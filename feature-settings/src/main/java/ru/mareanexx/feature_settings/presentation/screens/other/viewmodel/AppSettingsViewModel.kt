package ru.mareanexx.feature_settings.presentation.screens.other.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mareanexx.common.utils.AppSettings
import ru.mareanexx.feature_settings.domain.usecase.GetSettingsUseCase
import ru.mareanexx.feature_settings.domain.usecase.SetNotificationsUseCase
import ru.mareanexx.feature_settings.domain.usecase.SetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    private val setThemeUseCase: SetThemeUseCase,
    private val setNotificationsUseCase: SetNotificationsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    init { getSettings() }

    fun onThemeChanged(isLight: Boolean) {
        setTheme(isLight)
        _settings.value = _settings.value.copy(themeIsLight = isLight)
    }

    fun onNotificationsChanged(areOn: Boolean) {
        setNotifications(areOn)
        _settings.value = _settings.value.copy(notificationsAreOn = areOn)
    }

    private fun getSettings() {
        viewModelScope.launch {
            _settings.value = getSettingsUseCase()
        }
    }

    private fun setTheme(isLight: Boolean) {
        viewModelScope.launch {
            setThemeUseCase(isLight)
        }
    }

    private fun setNotifications(areOn: Boolean) {
        viewModelScope.launch {
            setNotificationsUseCase(areOn)
        }
    }
}