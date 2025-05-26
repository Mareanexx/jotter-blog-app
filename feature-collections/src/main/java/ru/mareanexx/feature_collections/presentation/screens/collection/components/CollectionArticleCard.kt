package ru.mareanexx.feature_collections.presentation.screens.collection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.utils.BuildConfig
import ru.mareanexx.common.utils.DateFormatter
import ru.mareanexx.feature_collections.R
import ru.mareanexx.feature_collections.data.remote.dto.CollectionArticle

@Composable
fun CollectionArticleCard(collectionArticle: CollectionArticle, onRemoveFromCollection: (collection: CollectionArticle) -> Unit) {
    val expandedMenu = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(height = 74.dp, width = 100.dp).clip(Shapes.extraSmall),
            model = "${BuildConfig.API_FILES_URL}${collectionArticle.photo}",
            contentDescription = stringResource(R.string.article_image),
            placeholder = painterResource(ru.mareanexx.core.common.R.drawable.image_placeholder),
            error = painterResource(ru.mareanexx.core.common.R.drawable.image_placeholder),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.weight(0.9f).padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = collectionArticle.title,
                style = MaterialTheme.typography.labelMedium.copy(lineHeight = 21.sp),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2, overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${collectionArticle.createdAt.format(DateFormatter.articleCreationDateFormatter)} - ${collectionArticle.readTimeSeconds} ${stringResource(R.string.min_read)}",
                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }
        Box {
            Button(
                modifier = Modifier.width(20.dp),
                shape = Shapes.extraSmall,
                contentPadding = PaddingValues(0.dp),
                onClick = { expandedMenu.value = !expandedMenu.value },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    painter = painterResource(R.drawable.more_vert_icon),
                    contentDescription = null, tint = MaterialTheme.colorScheme.onBackground
                )
            }

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
                    text = { Text(text = stringResource(R.string.remove_from_collection), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground) },
                    onClick = { onRemoveFromCollection(collectionArticle) },
                    trailingIcon = { Icon(painterResource(R.drawable.delete_icon), null, tint = MaterialTheme.colorScheme.onBackground) }
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}