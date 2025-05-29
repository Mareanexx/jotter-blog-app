package ru.mareanexx.feature_articles.presentation.screens.discover.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.utils.BuildConfig
import ru.mareanexx.common.utils.DateFormatter
import ru.mareanexx.feature_articles.R
import ru.mareanexx.feature_articles.data.remote.dto.DiscoverArticle

@Composable
fun ArticleCard(selected: DiscoverArticle) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth().clip(Shapes.extraSmall),
            model = "${BuildConfig.API_FILES_URL}${selected.photo}",
            placeholder = painterResource(ru.mareanexx.core.common.R.drawable.image_placeholder),
            error = painterResource(ru.mareanexx.core.common.R.drawable.image_placeholder),
            contentDescription = stringResource(R.string.upload_main_cover_photo),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = selected.title,
            style = MaterialTheme.typography.labelMedium.copy(lineHeight = 22.sp),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2, overflow = TextOverflow.Ellipsis
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(7.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = selected.createdAt.format(DateFormatter.articleDateFormatter),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Box(modifier = Modifier.size(4.dp).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), CircleShape))
            Text(
                text = DateFormatter.secondsToFormattedTime(selected.readTimeSeconds),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}