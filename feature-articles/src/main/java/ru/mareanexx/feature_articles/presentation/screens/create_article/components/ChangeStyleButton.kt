package ru.mareanexx.feature_articles.presentation.screens.create_article.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun StyleButton(
    @DrawableRes icon: Int,
    @StringRes contentDescription: Int,
    isStyled: Boolean,
    shape: Shape = RectangleShape,
    onChangeStyle: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(shape)
            .background(color = if (!isStyled) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f) else MaterialTheme.colorScheme.onSurface)
            .clickable(onClick = onChangeStyle)
            .padding(horizontal = 15.dp, vertical = 5.dp),
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.onBackground,
            painter = painterResource(icon),
            contentDescription = stringResource(contentDescription)
        )
    }
}