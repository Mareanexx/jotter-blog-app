package ru.mareanexx.feature_collections.presentation.screens.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.White
import ru.mareanexx.common.utils.BuildConfig
import ru.mareanexx.data.articles.dto.ArticleImage
import ru.mareanexx.feature_collections.R
import ru.mareanexx.feature_collections.data.remote.dto.CollectionWithArticleImages

@Composable
fun CollectionCard(
    collection: CollectionWithArticleImages,
    onNavigateToConcreteCollection: (collectionId: Int, collectionName: String) -> Unit,
    onGetArticles: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clip(Shapes.extraSmall)
            .clickable { onNavigateToConcreteCollection(collection.collection.id, collection.collection.name); onGetArticles() }
            .border(width = 2.dp, LightGray.copy(alpha = 0.3f), Shapes.extraSmall)
            .background(White.copy(alpha = 0.05f))
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 3.dp),
            text = collection.collection.name, color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = "${collection.collection.numberOfArticles} ${stringResource(R.string.articles)}",
            style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            collection.previewPhotos.forEach { articleImage ->
                CollectionCardArticleImage(articleImage)
            }
        }
    }
}

@Composable
fun CollectionCardArticleImage(articlePhoto: ArticleImage) {
    AsyncImage(
        modifier = Modifier.padding(top = 15.dp).size(height = 74.dp, width = 100.dp).clip(Shapes.extraSmall),
        model = "${BuildConfig.API_FILES_URL}${articlePhoto.photo}",
        placeholder = painterResource(ru.mareanexx.core.common.R.drawable.image_placeholder),
        error = painterResource(ru.mareanexx.core.common.R.drawable.image_placeholder),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(R.string.article_image)
    )
}