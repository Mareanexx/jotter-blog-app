package ru.mareanexx.feature_auth.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreenHeader(
    @StringRes title: Int,
    @StringRes description: Int,
    onNavigateBack: () -> Unit
) {
    Spacer(modifier = Modifier.height(40.dp))
    NavigateBackButton(onNavigateBack = onNavigateBack)
    Spacer(modifier = Modifier.height(40.dp))

    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = stringResource(title),
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Text(
        text = stringResource(description),
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(60.dp))
}