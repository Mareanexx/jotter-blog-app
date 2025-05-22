package ru.mareanexx.feature_auth.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.common.ui.theme.LightBlue
import ru.mareanexx.common.ui.theme.Shapes

@Composable
fun CustomMainButton(
    isShadowed: Boolean = false,
    isLoading: Boolean = false,
    @StringRes buttonText: Int,
    containerColor: Color,
    textColor: Color,
    onAuthClicked: () -> Unit
) {
    val buttonModifier = if (isShadowed) Modifier.fillMaxWidth().addBlueShadow() else Modifier.fillMaxWidth()

    Button(
        shape = ru.mareanexx.common.ui.theme.Shapes.extraLarge,
        modifier = buttonModifier,
        onClick = onAuthClicked,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        contentPadding = PaddingValues(vertical = 17.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = MaterialTheme.colorScheme.onPrimary)
        } else {
            Text(
                text = stringResource(buttonText).uppercase(),
                style = MaterialTheme.typography.bodyMedium.copy(letterSpacing = 0.5.sp), color = textColor
            )
        }
    }
}

fun Modifier.addBlueShadow() = shadow(elevation = 15.dp, shape = ru.mareanexx.common.ui.theme.Shapes.extraLarge, ambientColor = ru.mareanexx.common.ui.theme.LightBlue, spotColor = ru.mareanexx.common.ui.theme.LightBlue)