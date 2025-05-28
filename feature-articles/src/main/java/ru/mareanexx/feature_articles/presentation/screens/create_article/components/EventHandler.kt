package ru.mareanexx.feature_articles.presentation.screens.create_article.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.feature_articles.presentation.components.SuccessCreateArticleDialog
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.event.CreateArticleEvent

@Composable
fun CreateArticleEventHandler(
    eventFlow: SharedFlow<CreateArticleEvent>,
    onNavigateNextPage: () -> Unit = {},
    onClosedClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is CreateArticleEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                CreateArticleEvent.NextPage -> { onNavigateNextPage() }
                CreateArticleEvent.ShowSuccessDialog -> { showDialog.value = true }
            }
        }
    }

    if (showDialog.value) {
        SuccessCreateArticleDialog(
            onDismissRequest = { showDialog.value = false; onClosedClicked() }
        )
    }
}