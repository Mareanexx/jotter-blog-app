package ru.mareanexx.feature_collections.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.common.components.CustomMainButton
import ru.mareanexx.common.ui.common.components.CustomTextField
import ru.mareanexx.common.ui.theme.DarkBlue
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.feature_collections.R
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.CollectionsViewModel
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.state.CollectionsUiState

@Composable
fun CollectionBottomSheetContent(
    @StringRes title: Int,
    @StringRes buttonText: Int,
    onDismissRequest: () -> Unit,
    onMainButtonClicked: () -> Unit,
    collectionsViewModel: CollectionsViewModel
) {
    val uiState = collectionsViewModel.collectionsUiState.collectAsState()
    val newCollectionForm = collectionsViewModel.newCollectionForm.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Box(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    shape = Shapes.extraSmall
                ).clickable(onClick = onDismissRequest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = DarkBlue,
                    contentDescription = stringResource(R.string.close_sheet_dsc)
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        CustomTextField(
            value = newCollectionForm.value.name,
            onValueChanged = { collectionsViewModel.onCollectionNameChanged(it) },
            label = R.string.tf_label_collection_name,
            placeholder = R.string.tf_desc_enter_collection_name,
            icon = ru.mareanexx.core.common.R.drawable.title_icon,
            isError = newCollectionForm.value.nameValidationError != null,
            errorRes = newCollectionForm.value.nameValidationError?.textErr ?: R.string.empty_string,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(30.dp))
        
        CustomMainButton(
            isShadowed = true,
            isLoading = uiState.value == CollectionsUiState.Loading,
            buttonText = buttonText,
            containerColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            onAuthClicked = onMainButtonClicked
        )
        Spacer(modifier = Modifier.height(45.dp))
    }
}