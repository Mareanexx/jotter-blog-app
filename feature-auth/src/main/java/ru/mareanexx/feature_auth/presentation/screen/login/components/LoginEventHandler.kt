package ru.mareanexx.feature_auth.presentation.screen.login.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.event.LoginEvent

@Composable
fun LoginEventHandler(eventFlow: SharedFlow<LoginEvent>) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is LoginEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}