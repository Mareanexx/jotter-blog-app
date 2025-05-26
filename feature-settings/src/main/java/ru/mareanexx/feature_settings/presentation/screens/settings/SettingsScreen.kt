package ru.mareanexx.feature_settings.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.common.components.ScreenHeader
import ru.mareanexx.common.ui.common.components.HeaderRowIcons
import ru.mareanexx.feature_settings.R
import ru.mareanexx.feature_settings.presentation.screens.settings.components.SettingsListItem

enum class SettingsRoute(val route: String) {
    Profile("profile"),
    Others("others")
}

@Composable
fun SettingsScreen(onNavigateToSettings: (SettingsRoute) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {
        HeaderRowIcons()
        ScreenHeader(R.string.settings_title, R.string.settings_description, paddingTop = 15.dp)

        SettingsListItem(
            titleRes = R.string.profile_settings_lbl,
            descRes = R.string.profile_settings_desc,
            icon = ru.mareanexx.core.common.R.drawable.person_icon,
            onNavigateToSettings = { onNavigateToSettings(SettingsRoute.Profile) }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
        SettingsListItem(
            titleRes = R.string.other_settings_lbl,
            descRes = R.string.other_settings_desc,
            icon = R.drawable.extension_icon,
            onNavigateToSettings = { onNavigateToSettings(SettingsRoute.Others) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen {}
}