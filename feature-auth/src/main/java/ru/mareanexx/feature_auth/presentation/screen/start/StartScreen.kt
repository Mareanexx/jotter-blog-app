package ru.mareanexx.feature_auth.presentation.screen.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mareanexx.feature_auth.R
import ru.mareanexx.common.ui.common.components.CustomMainButton

@Composable
fun StartScreen(onNavigateToLoginScreen: () -> Unit, onNavigateToRegisterScreen: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 15.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 150.dp),
            verticalArrangement = Arrangement.spacedBy(35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(150.dp).clip(ru.mareanexx.common.ui.theme.Shapes.extraLarge),
                painter = painterResource(R.drawable.jotter_app_icon),
                contentDescription = stringResource(R.string.main_app_icon)
            )
            Text(
                text = stringResource(R.string.start_screen_description),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface
            )
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().systemBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomMainButton(
                buttonText = R.string.log_in_lbl, containerColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary, onAuthClicked = onNavigateToLoginScreen
            )
            CustomMainButton(
                buttonText = R.string.sign_up_lbl, containerColor = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onSecondary, onAuthClicked = onNavigateToRegisterScreen
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewStartScreen() {
    StartScreen({}, {})
}