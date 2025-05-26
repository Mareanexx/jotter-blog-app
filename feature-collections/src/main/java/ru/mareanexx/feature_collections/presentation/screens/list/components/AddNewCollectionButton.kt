package ru.mareanexx.feature_collections.presentation.screens.list.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.feature_collections.R

@Composable
fun AddNewCollectionButton(onAddNewCollection: () -> Unit) {
    IconButton(
        modifier = Modifier.size(55.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onAddNewCollection
    ) {
        Icon(
            painter = painterResource(ru.mareanexx.core.common.R.drawable.add_icon),
            contentDescription = stringResource(R.string.create_a_collection)
        )
    }
}