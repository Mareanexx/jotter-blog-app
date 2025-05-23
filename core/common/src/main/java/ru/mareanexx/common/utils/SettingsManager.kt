package ru.mareanexx.common.utils

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class AppSettings(
    val themeIsLight: Boolean = true,
    val notificationsAreOn: Boolean = true
)

class SettingsManager @Inject constructor(
    private val dataStore: DataStore
) {
    val themeSettings: Flow<Boolean> = dataStore.trackTheme()

    suspend fun setTheme(isLight: Boolean) = dataStore.setTheme(isLight = isLight)
    suspend fun setNotifications(state: Boolean) = dataStore.setNotifications(isOn = state)

    suspend fun getAppSettings(): AppSettings {
        val themeIsLight = dataStore.getTheme()
        val notificationsAreOn = dataStore.getNotifications()
        return AppSettings(themeIsLight, notificationsAreOn)
    }
}