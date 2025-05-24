package ru.mareanexx.feature_settings.presentation.screens.profile.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.common.ui.common.components.BirthDatePickerDialog
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.event.ProfileSettingsEvent

@Composable
fun ProfileSettingsEventHandler(
    eventFlow: SharedFlow<ProfileSettingsEvent>,
    onBirthDateChanged: (Long) -> Unit
) {
    val showDatePicker = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is ProfileSettingsEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                ProfileSettingsEvent.ShowDatePicker -> { showDatePicker.value = true }
            }
        }
    }

    if (showDatePicker.value) {
        BirthDatePickerDialog(
            onDismiss = { showDatePicker.value = false },
            onDateSelected = { onBirthDateChanged(it); showDatePicker.value = false }
        )
    }
}