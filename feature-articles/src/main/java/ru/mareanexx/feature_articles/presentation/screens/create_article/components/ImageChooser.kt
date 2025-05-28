package ru.mareanexx.feature_articles.presentation.screens.create_article.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.theme.DarkBlue
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.feature_articles.R
import java.io.File

@Composable
fun CoverImage(onUploadCoverPhoto: () -> Unit, coverPhoto: File?) {
    Column(modifier = Modifier.fillMaxWidth().height(180.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(
            text = stringResource(R.string.photo_chooser_label), style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface, maxLines = 1
        )
        if (coverPhoto != null) {
            ChosenArticleImage(coverPhoto = coverPhoto, onReloadCoverPhoto = onUploadCoverPhoto)
        } else {
            CoverImageFirstChooser(onUploadCoverPhoto = onUploadCoverPhoto)
        }
    }
}

@Composable
fun ChosenArticleImage(coverPhoto: File, onReloadCoverPhoto: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().clip(Shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = coverPhoto,
            contentDescription = stringResource(R.string.upload_main_cover_photo),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 3.dp)
                .clickable(onClick = onReloadCoverPhoto)
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                    shape = Shapes.small
                )
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                tint = DarkBlue,
                painter = painterResource(R.drawable.add_photo_icon),
                contentDescription = stringResource(R.string.upload_main_cover_photo)
            )
            Text(
                text = stringResource(R.string.reload_cover_image),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = DarkBlue
            )
        }
    }
}

@Composable
fun CoverImageFirstChooser(onUploadCoverPhoto: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().clip(Shapes.medium)
            .clickable(onClick = onUploadCoverPhoto)
            .background(LightGray.copy(alpha = 0.15f), Shapes.medium)
            .padding(vertical = 35.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.add_photo_icon),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.upload_main_cover_photo)
            )
            Text(
                text = stringResource(R.string.upload_main_cover_photo),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.image_size_requirements),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}