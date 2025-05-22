package ru.mareanexx.feature_auth.presentation.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.feature_auth.R
import ru.mareanexx.feature_auth.presentation.components.AuthBottomSuggestionText
import ru.mareanexx.feature_auth.presentation.components.AuthScreenHeader
import ru.mareanexx.feature_auth.presentation.components.CustomAuthTextField
import ru.mareanexx.feature_auth.presentation.screen.login.components.LoginEventHandler
import ru.mareanexx.feature_auth.presentation.screen.start.CustomMainButton
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.LoginViewModel
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.state.LoginUiState

@Composable
fun LoginScreen(
    onNavigateToRegisterScreen: () -> Unit, onNavigateBack: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit
) {
    val uiState = loginViewModel.uiState.collectAsState()
    val formState = loginViewModel.formState.collectAsState()

    LoginEventHandler(loginViewModel.eventFlow)

    if (uiState.value == LoginUiState.Success) onNavigateToProfile()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background).padding(top = 20.dp)
            .padding(horizontal = 15.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            AuthScreenHeader(R.string.log_in_lbl, R.string.log_in_description, onNavigateBack = onNavigateBack)

            CustomAuthTextField(
                value = formState.value.email,
                onValueChanged = { loginViewModel.onEmailChanged(it) },
                label = R.string.tf_label_email,
                placeholder = R.string.tf_placeholder_email,
                icon = R.drawable.email_icon,
                isError = uiState.value == LoginUiState.Error,
                errorRes = R.string.incorrect_credentials,
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomAuthTextField(
                value = formState.value.password,
                onValueChanged = { loginViewModel.onPasswordChanged(it) },
                label = R.string.tf_label_password,
                placeholder = R.string.tf_placeholder_password,
                icon = R.drawable.password_icon,
                isPassword = true,
                isError = uiState.value == LoginUiState.Error,
                errorRes = R.string.incorrect_credentials,
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            )
        }

        Column(
            modifier = Modifier.padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            CustomMainButton(
                isShadowed = true,
                buttonText = R.string.log_in_lbl, containerColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary, onAuthClicked = { loginViewModel.login() }
            )
            AuthBottomSuggestionText(R.string.new_user_log, R.string.sign_up_lbl, onNavigateTo = onNavigateToRegisterScreen)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen({}, {}, onNavigateToProfile = {})
}