package ru.mareanexx.feature_auth.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.core.ui.theme.Shapes
import ru.mareanexx.feature_auth.R

@Composable
fun NavigateBackButton(onNavigateBack: () -> Unit) {
    Button(
        modifier = Modifier.size(38.dp),
        contentPadding = PaddingValues(horizontal = 0.dp),
        shape = Shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        onClick = onNavigateBack
    ) {
        Icon(
            modifier = Modifier.size(36.dp), tint = MaterialTheme.colorScheme.onSecondary,
            painter = painterResource(R.drawable.back_icon),
            contentDescription = stringResource(R.string.navigate_back_dsc)
        )
    }
}