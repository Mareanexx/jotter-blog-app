package ru.mareanexx.feature_articles.presentation.screens.discover.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mareanexx.feature_articles.R
import ru.mareanexx.feature_articles.data.remote.dto.CategoryArticles

@Composable
fun AnimatedCategoryNameHeader(showHeader: Boolean, selectedCategory: State<CategoryArticles?>, onNavigateToSearchScreen: () -> Unit) {
    val alpha by animateFloatAsState(if (showHeader) 1f else 0.2f, label = "alphaAnim")
    val animateHeaderHeight = animateDpAsState(if (showHeader) 25.dp else 0.dp, animationSpec = tween(200), label = "headerHeightAnim")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .graphicsLayer {
                this.alpha = alpha
            }
            .padding(vertical = 15.dp, horizontal = 20.dp)
            .height(animateHeaderHeight.value),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = selectedCategory.value?.category?.name ?: stringResource(R.string.empty_string),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            modifier = Modifier
                .size(26.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onNavigateToSearchScreen() },
            painter = painterResource(ru.mareanexx.core.common.R.drawable.search_icon),
            contentDescription = null, tint = MaterialTheme.colorScheme.onBackground
        )
    }
}