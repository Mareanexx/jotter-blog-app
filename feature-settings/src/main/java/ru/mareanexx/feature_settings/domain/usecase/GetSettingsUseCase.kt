package ru.mareanexx.feature_settings.domain.usecase

import ru.mareanexx.common.utils.SettingsManager
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsManager: SettingsManager
) {
    suspend operator fun invoke() = settingsManager.getAppSettings()
}