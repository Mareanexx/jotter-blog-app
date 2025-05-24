package ru.mareanexx.common.ui.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.core.common.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDatePickerDialog(onDismiss: () -> Unit, onDateSelected: (birthdate: Long) -> Unit) {

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val today = LocalDate.now(ZoneOffset.UTC)
                val date = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneOffset.UTC).toLocalDate()
                return !date.isAfter(today)
            }
        }
    )

    val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(
        colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.background),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { datePickerState.selectedDateMillis?.let { onDateSelected(it) } },
                enabled = confirmEnabled
            ) { Text(stringResource(R.string.ok_btn), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel_btn), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground) }
        }
    ) {
        DatePicker(
            colors = DatePickerDefaults.colors(dateTextFieldColors = TextFieldDefaults.colors(focusedTextColor = Color.Black, unfocusedTextColor = Color.Black, unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)),
            state = datePickerState,
            title = {
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 20.dp),
                    text = stringResource(R.string.select_birth_date), style = MaterialTheme.typography.labelLarge
                )
            },
        )
    }
}

@Composable
fun BirthDateChooser(birthdate: LocalDate?, onOpenDatePicker: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(
            text = stringResource(R.string.tf_label_birthdate), style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            BirthDateOneComponent(
                Modifier.weight(0.3f), value = birthdate?.dayOfMonth,
                R.string.f_placeholder_day, onOpenDatePicker
            )
            BirthDateOneComponent(
                Modifier.weight(0.3f), value = birthdate?.monthValue,
                R.string.f_placeholder_month, onOpenDatePicker
            )
            BirthDateOneComponent(
                Modifier.weight(0.3f), value = birthdate?.year,
                R.string.f_placeholder_year, onOpenDatePicker
            )
        }
    }
}

@Composable
fun BirthDateOneComponent(
    weightModifier: Modifier,
    value: Int? = null,
    @StringRes label: Int,
    onOpenDatePicker: () -> Unit
) {
    Box(
        modifier = weightModifier
            .background(LightGray.copy(alpha = 0.15f), shape = Shapes.medium)
            .clickable { onOpenDatePicker() }
            .padding(horizontal = 15.dp, vertical = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${value ?: stringResource(label)}",
            style = MaterialTheme.typography.bodyMedium,
            color = if (value != null) MaterialTheme.colorScheme.surfaceBright else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}