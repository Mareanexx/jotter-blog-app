package ru.mareanexx.feature_articles.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ru.mareanexx.common.ui.common.components.CustomMainButton
import ru.mareanexx.feature_articles.R

@Composable
fun BottomButtonAndStepNumber(
    isLoading: Boolean,
    stepNumber: Int,
    containerColor: Color = MaterialTheme.colorScheme.onBackground,
    contentColor: Color = MaterialTheme.colorScheme.background,
    @StringRes buttonText: Int,
    onButtonClicked: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        Text(
            modifier = Modifier.weight(0.4f),
            textAlign = TextAlign.Center,
            text = "$stepNumber ${stringResource(R.string.of)} 2",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(modifier = Modifier.weight(0.6f)) {
            CustomMainButton(
                isLoading = isLoading,
                buttonText = buttonText,
                containerColor = containerColor,
                textColor = contentColor,
                onAuthClicked = onButtonClicked
            )
        }
    }
}