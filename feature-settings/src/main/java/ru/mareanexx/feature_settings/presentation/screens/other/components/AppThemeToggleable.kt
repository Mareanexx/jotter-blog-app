package ru.mareanexx.feature_settings.presentation.screens.other.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.feature_settings.R

enum class AppTheme(@StringRes val label: Int) {
    Light(R.string.light_theme_lbl),
    Dark(R.string.dark_theme_lbl)
}

@Composable
fun AppThemeToggleable(themeIsLight: Boolean, onThemeChanged: (Boolean) -> Unit) {

    val selectedOption = if (themeIsLight) AppTheme.Light else AppTheme.Dark
    val radioOptions = listOf(AppTheme.Light, AppTheme.Dark)

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.app_theme_set_title),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            radioOptions.forEach { radioType ->
                Row(
                    modifier = Modifier.weight(0.5f).clip(Shapes.medium)
                        .selectable(
                            selected = (radioType == selectedOption),
                            onClick = { onThemeChanged(radioType == AppTheme.Light) },
                            role = Role.RadioButton
                        )
                        .background(
                            if (radioType == selectedOption) LightGray.copy(alpha = 0.15f)
                            else MaterialTheme.colorScheme.background,
                            shape = Shapes.medium
                        )
                        .border(width = 2.dp, color = LightGray.copy(alpha = 0.15f), shape = Shapes.medium)
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = LightGray.copy(alpha = 0.15f)
                        ),
                        selected = (radioType == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = stringResource(radioType.label),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}