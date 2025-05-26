package ru.mareanexx.feature_collections.presentation.screens.collection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.feature_collections.R

@Composable
fun CollectionSettingsButton(onDeleteCollection: () -> Unit, onRenameCollection: () -> Unit) {
    val expandedMenu = remember { mutableStateOf(false) }

    Box {
        Icon(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { expandedMenu.value = !expandedMenu.value },
            painter = painterResource(ru.mareanexx.core.common.R.drawable.settings_wheel_icon),
            contentDescription = null, tint = MaterialTheme.colorScheme.onBackground
        )

        DropdownMenu(
            modifier = Modifier,
            shape = Shapes.small,
            expanded = expandedMenu.value,
            containerColor = MaterialTheme.colorScheme.background,
            border = BorderStroke(width = 2.dp, LightGray.copy(alpha = 0.3f)),
            onDismissRequest = { expandedMenu.value = !expandedMenu.value }
        ) {
            DropdownMenuItem(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = { Text(text = stringResource(R.string.rename_collection), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground) },
                onClick = onRenameCollection,
                trailingIcon = { Icon(painterResource(ru.mareanexx.core.common.R.drawable.title_icon), null, tint = MaterialTheme.colorScheme.onBackground) }
            )
            DropdownMenuItem(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = { Text(text = stringResource(R.string.delete_collection), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground) },
                onClick = onDeleteCollection,
                trailingIcon = { Icon(painterResource(R.drawable.delete_icon), null, tint = MaterialTheme.colorScheme.onBackground) }
            )
        }
    }
}