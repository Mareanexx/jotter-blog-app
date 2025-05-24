package ru.mareanexx.feature_settings.presentation.screens.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.common.components.BirthDateChooser
import ru.mareanexx.common.ui.common.components.CustomMainButton
import ru.mareanexx.common.ui.common.components.CustomTextField
import ru.mareanexx.common.utils.DateFormatter
import ru.mareanexx.common.utils.FileUtils.uriToFile
import ru.mareanexx.feature_settings.R
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.ProfileSettingsViewModel
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.form.ProfileForm
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.state.Field
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.state.ProfileSettingsUiState

@Composable
fun ProfileSettingsLoadedContent(
    profileData: ProfileForm,
    uiState: ProfileSettingsUiState,
    viewModel: ProfileSettingsViewModel
) {
    val context = LocalContext.current
    val fieldErrors = viewModel.validationErrors.collectAsState()

    val usernameError = fieldErrors.value[Field.USERNAME]
    val emailError = fieldErrors.value[Field.EMAIL]
    val bioError = fieldErrors.value[Field.BIO]

    val avatarLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(uri, context)
            viewModel.onAvatarSelected(file)
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        ProfileAvatar(profileData, onLaunchNewPhoto = { avatarLauncher.launch("image/*") })

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = profileData.username,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier.padding(top = 7.dp, bottom = 30.dp),
            text = "${stringResource(R.string.member_since)} ${profileData.createdAt?.format(
                DateFormatter.profileCreationDateFormatter)}",
            style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface
        )

        CustomTextField(
            value = profileData.email,
            onValueChanged = { viewModel.onEmailChanged(it) },
            label = R.string.tf_label_email,
            placeholder = R.string.tf_placeholder_email,
            icon = R.drawable.email_icon,
            isError = emailError != null,
            errorRes = emailError?.message ?: R.string.empty_string,
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            value = profileData.username,
            onValueChanged = { viewModel.onUsernameChanged(it) },
            label = R.string.tf_username_label,
            placeholder = R.string.tf_username_placeholder,
            icon = ru.mareanexx.core.common.R.drawable.person_icon,
            isError = bioError != null,
            errorRes = usernameError?.message ?: R.string.empty_string,
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            value = profileData.bio,
            onValueChanged = { viewModel.onBioChanged(it) },
            label = R.string.tf_bio_label,
            placeholder = R.string.tf_bio_placeholder,
            icon = R.drawable.edit_icon,
            isError = bioError != null,
            errorRes = bioError?.message ?: R.string.empty_string,
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(20.dp))

        BirthDateChooser(
            birthdate = profileData.birthdate,
            onOpenDatePicker = { viewModel.showDatePickerDialog() }
        )
        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier.padding(horizontal = 80.dp, vertical = 20.dp)) {
            CustomMainButton(
                isShadowed = true,
                isLoading = uiState == ProfileSettingsUiState.Loading,
                buttonText = R.string.save,
                containerColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                onAuthClicked = { viewModel.updateProfile() }
            )
        }
    }
}