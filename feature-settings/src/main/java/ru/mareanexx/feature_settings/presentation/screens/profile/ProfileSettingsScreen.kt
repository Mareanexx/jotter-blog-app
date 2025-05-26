package ru.mareanexx.feature_settings.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.common.components.ErrorRetry
import ru.mareanexx.common.ui.common.components.SmallScreenHeader
import ru.mareanexx.feature_settings.R
import ru.mareanexx.feature_settings.presentation.components.ProfileSettingsSkeleton
import ru.mareanexx.feature_settings.presentation.screens.profile.components.ProfileSettingsEventHandler
import ru.mareanexx.feature_settings.presentation.screens.profile.components.ProfileSettingsLoadedContent
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.ProfileSettingsViewModel
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.state.ProfileSettingsUiState

@Composable
fun ProfileSettingsScreen(
    onNavigateBack: () -> Unit,
    profileSettingsViewModel: ProfileSettingsViewModel = hiltViewModel()
) {
    val uiState by profileSettingsViewModel.uiState.collectAsState()
    val profileData by profileSettingsViewModel.profileForm.collectAsState()

    ProfileSettingsEventHandler(
        eventFlow = profileSettingsViewModel.eventFlow,
        onBirthDateChanged = { profileSettingsViewModel.onBirthDateChanged(it) }
    )

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        SmallScreenHeader(stringResource(R.string.profile_settings_lbl), onNavigateBack)

        Spacer(Modifier.height(20.dp))

        when(uiState) {
            ProfileSettingsUiState.Loading -> { ProfileSettingsSkeleton() }
            ProfileSettingsUiState.Showing -> {
                ProfileSettingsLoadedContent(profileData, uiState, profileSettingsViewModel)
            }
            ProfileSettingsUiState.Error -> {
                ErrorRetry(onRetry = { profileSettingsViewModel.retry() })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileSettingsScreen() {
    ProfileSettingsScreen({})
}