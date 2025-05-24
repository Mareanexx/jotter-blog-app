package ru.mareanexx.feature_settings.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.common.components.NavigateBackButton

@Composable
fun ConcreteSettingsHeader(@StringRes title: Int, onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 6.dp, end = 50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigateBackButton(onNavigateBack = onNavigateBack)
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = stringResource(title),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}