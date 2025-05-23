package ru.mareanexx.feature_auth.presentation.screen.register

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
import ru.mareanexx.common.ui.common.components.AuthScreenHeader
import ru.mareanexx.feature_auth.R
import ru.mareanexx.feature_auth.presentation.components.AuthBottomSuggestionText
import ru.mareanexx.feature_auth.presentation.components.CustomAuthTextField
import ru.mareanexx.feature_auth.presentation.components.CustomMainButton
import ru.mareanexx.feature_auth.presentation.screen.register.components.BirthDateChooser
import ru.mareanexx.feature_auth.presentation.screen.register.components.RegistrationEventHandler
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.RegisterViewModel
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.state.Field
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.state.RegisterUiState

@Composable
fun RegisterScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToLoginScreen: () -> Unit, onNavigateBack: () -> Unit,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState = registerViewModel.uiState.collectAsState()
    val formState = registerViewModel.formState.collectAsState()
    val fieldErrors = (uiState.value as? RegisterUiState.ValidationError)?.fieldError ?: emptyMap()

    val usernameError = fieldErrors[Field.USERNAME]
    val emailError = fieldErrors[Field.EMAIL]
    val passwordError = fieldErrors[Field.PASSWORD]
    val confirmPasswordError = fieldErrors[Field.CONF_PASSWORD]

    RegistrationEventHandler(registerViewModel.eventFlow, onBirthDateChanged = { registerViewModel.onBirthDateChanged(it) })

    if (uiState.value == RegisterUiState.Success) onNavigateToProfile()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background).padding(top = 20.dp)
            .padding(horizontal = 15.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            AuthScreenHeader(R.string.sign_up_lbl, R.string.sign_up_description, onNavigateBack = onNavigateBack)

            CustomAuthTextField(
                value = formState.value.username,
                onValueChanged = { registerViewModel.onUsernameChanged(it) },
                label = R.string.tf_label_username,
                placeholder = R.string.tf_placeholder_username,
                icon = R.drawable.person_icon,
                isError = usernameError != null,
                errorRes = usernameError?.message ?: R.string.empty_string,
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomAuthTextField(
                value = formState.value.email,
                onValueChanged = { registerViewModel.onEmailChanged(it) },
                label = R.string.tf_label_email,
                placeholder = R.string.tf_placeholder_email,
                icon = R.drawable.email_icon,
                isError = emailError != null,
                errorRes = emailError?.message ?: R.string.empty_string,
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomAuthTextField(
                value = formState.value.password,
                onValueChanged = { registerViewModel.onPasswordChanged(it) },
                label = R.string.tf_label_password,
                placeholder = R.string.tf_placeholder_password,
                icon = R.drawable.password_icon,
                isPassword = true,
                isError = passwordError != null,
                errorRes = passwordError?.message ?: R.string.empty_string,
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomAuthTextField(
                value = formState.value.confirmPassword,
                onValueChanged = { registerViewModel.onConfirmPasswordChanged(it) },
                label = R.string.tf_label_confirm_password,
                placeholder = R.string.tf_placeholder_confirm_password,
                icon = R.drawable.password_icon,
                isPassword = true,
                isError = confirmPasswordError != null,
                errorRes = confirmPasswordError?.message ?: R.string.empty_string,
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(20.dp))

            BirthDateChooser(formState, onOpenDatePicker = { registerViewModel.showDatePickerDialog() })
        }

        Column(
            modifier = Modifier.padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            CustomMainButton(
                isShadowed = true,
                isLoading = uiState.value == RegisterUiState.Loading,
                buttonText = R.string.sign_up_lbl, containerColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary, onAuthClicked = { registerViewModel.register() }
            )
            AuthBottomSuggestionText(R.string.already_registered, R.string.log_in_lbl, onNavigateTo = onNavigateToLoginScreen)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewRegisterScreen() {
    RegisterScreen({}, {}, {})
}