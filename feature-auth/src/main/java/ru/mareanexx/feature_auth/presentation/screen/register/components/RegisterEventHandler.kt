package ru.mareanexx.feature_auth.presentation.screen.register.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.feature_auth.presentation.screen.register.BirthDatePickerDialog
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.event.RegistrationEvent

@Composable
fun RegistrationEventHandler(
    eventFlow: SharedFlow<RegistrationEvent>,
    onBirthDateChanged: (Long) -> Unit
) {
    val showDatePicker = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                RegistrationEvent.ShowDatePicker -> { showDatePicker.value = true }
                is RegistrationEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
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