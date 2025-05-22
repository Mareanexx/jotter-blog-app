package ru.mareanexx.feature_auth.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.common.ui.theme.DarkGray

@Composable
fun AuthBottomSuggestionText(
    @StringRes first: Int,
    @StringRes actionText: Int,
    onNavigateTo: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 13.sp),
            text = stringResource(first), color = ru.mareanexx.common.ui.theme.DarkGray
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            modifier = Modifier.clickable { onNavigateTo() },
            text = stringResource(actionText), color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 13.sp)
        )
    }
}