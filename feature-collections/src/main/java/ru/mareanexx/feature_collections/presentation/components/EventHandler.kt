package ru.mareanexx.feature_collections.presentation.components

import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.common.ui.common.components.DeleteConfirmationDialog
import ru.mareanexx.feature_collections.R
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.CollectionsViewModel
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.event.BottomSheetType
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.event.CollectionsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionEventHandler(
    eventFlow: SharedFlow<CollectionsEvent>,
    onClearForm: () -> Unit,
    onDeleteConfirmed: () -> Unit = {},
    onAddNewCollection: () -> Unit = {},
    onUpdateCollectionName: () -> Unit = {},
    collectionsViewModel: CollectionsViewModel
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val showBottomSheet = remember { mutableStateOf(false) }
    val bottomSheetType = remember { mutableStateOf(BottomSheetType.Create) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is CollectionsEvent.ShowBottomSheet -> {
                    bottomSheetType.value = event.type
                    showBottomSheet.value = true
                }
                is CollectionsEvent.ShowToast -> { Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show() }
                CollectionsEvent.ShowDeleteConfirmationDialog -> { showDialog.value = true }
            }
        }
    }
    if (showBottomSheet.value) {
        ModalBottomSheet(
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { showBottomSheet.value = false; onClearForm() }
        ) {
            when(bottomSheetType.value) {
                BottomSheetType.None -> { showBottomSheet.value = false; onClearForm() }
                BottomSheetType.Create -> {
                    CollectionBottomSheetContent(
                        onDismissRequest = { showBottomSheet.value = false; onClearForm() },
                        title = R.string.create_a_collection,
                        buttonText = R.string.create_collection_btn,
                        onMainButtonClicked = onAddNewCollection,
                        collectionsViewModel = collectionsViewModel
                    )
                }
                BottomSheetType.Update -> {
                    CollectionBottomSheetContent(
                        onDismissRequest = { showBottomSheet.value = false; onClearForm() },
                        title = R.string.update_collection_name_title,
                        buttonText = R.string.save_changes_btn,
                        onMainButtonClicked = onUpdateCollectionName,
                        collectionsViewModel = collectionsViewModel
                    )
                }
            }
        }
    }
    if (showDialog.value) {
        DeleteConfirmationDialog(
            additional = ru.mareanexx.core.common.R.string.collection_variant_del,
            onCancelClicked = { showDialog.value = false },
            onDeleteClicked = { onDeleteConfirmed(); showDialog.value = false }
        )
    }
}