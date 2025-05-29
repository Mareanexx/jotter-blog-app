package ru.mareanexx.feature_articles.presentation.screens.discover.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.feature_articles.data.remote.dto.CategoryArticles

@Composable
fun CategoryChip(
    selectedCategory: CategoryArticles?,
    category: CategoryArticles,
    onChangeSelectedCategory: (CategoryArticles) -> Unit
) {
    FilterChip(
        modifier = Modifier,
        shape = Shapes.medium,
        selected = selectedCategory == category,
        onClick = { onChangeSelectedCategory(category) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.15f),
            labelColor = MaterialTheme.colorScheme.onBackground,
            selectedContainerColor = MaterialTheme.colorScheme.onBackground,
            selectedLabelColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(0.3f)),
        label = {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = category.category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = if (selectedCategory != category) MaterialTheme.colorScheme.onSurface.copy(0.6f) else MaterialTheme.colorScheme.background
            )
        }
    )
}