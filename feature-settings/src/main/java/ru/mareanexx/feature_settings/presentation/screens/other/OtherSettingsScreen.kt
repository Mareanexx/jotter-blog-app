package ru.mareanexx.feature_settings.presentation.screens.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.common.components.SmallScreenHeader
import ru.mareanexx.feature_settings.R
import ru.mareanexx.feature_settings.presentation.screens.other.components.AppThemeToggleable
import ru.mareanexx.feature_settings.presentation.screens.other.components.OtherSettingSwitchable
import ru.mareanexx.feature_settings.presentation.screens.other.viewmodel.AppSettingsViewModel

@Composable
fun OtherSettingsScreen(
    appSettingsViewModel: AppSettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val appSettings = appSettingsViewModel.settings.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        SmallScreenHeader(stringResource(R.string.other_settings_lbl), onNavigateBack)

        Spacer(modifier = Modifier.height(10.dp))

        OtherSettingSwitchable(
            appSettingState = appSettings.value.notificationsAreOn,
            onStateChanged = { appSettingsViewModel.onNotificationsChanged(it) }
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

        AppThemeToggleable(
            themeIsLight = appSettings.value.themeIsLight,
            onThemeChanged = { appSettingsViewModel.onThemeChanged(it) }
        )
    }
}