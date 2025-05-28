package ru.mareanexx.feature_articles.presentation.screens.create_article.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.common.components.CustomTextField
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.feature_articles.R
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.presentation.components.skeleton.CategoriesSkeleton
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.form.CreateArticleForm
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.ArticleField
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.CategoriesUiState

@Composable
fun CategoriesChooserRow(
    newArticleForm: State<CreateArticleForm>,
    onSearchChanged: (String) -> Unit,
    onRemoveFromSelected: (category: Category) -> Unit,
    onAddToSelected: (category: Category) -> Unit,
    uiState: State<CategoriesUiState>
) {
    val categoryError = newArticleForm.value.fieldError[ArticleField.Categories]

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {

        CustomTextField(
            value = newArticleForm.value.categorySearch,
            onValueChanged = onSearchChanged,
            label = R.string.tf_categories_label,
            placeholder = R.string.tf_categories_placeholder,
            isError = categoryError != null,
            errorRes = categoryError?.errorText ?: R.string.empty_string,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )

        when(uiState.value) {
            CategoriesUiState.LoadingCategories -> { CategoriesSkeleton() }
            CategoriesUiState.ErrorLoadingCategories -> {
                Text(
                    text = stringResource(R.string.error_loading_categories),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onError
                )
            }
            CategoriesUiState.Showing -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(newArticleForm.value.selectedCategories) { category ->
                        CustomFilterChip(
                            category, newArticleForm.value.selectedCategories,
                            onRemoveFromSelected = onRemoveFromSelected
                        )
                    }
                    items(newArticleForm.value.shownCategories) { category ->
                        CustomFilterChip(
                            category, newArticleForm.value.selectedCategories,
                            onAddToSelected = onAddToSelected
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomFilterChip(
    category: Category,
    selectedValues: List<Category>,
    onAddToSelected: (category: Category) -> Unit = {},
    onRemoveFromSelected: (category: Category) -> Unit = {}
) {
    FilterChip(
        shape = Shapes.medium,
        selected = category in selectedValues,
        onClick = { if(category !in selectedValues) onAddToSelected(category) else onRemoveFromSelected(category) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.15f),
            labelColor = MaterialTheme.colorScheme.onBackground,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(0.4f)),
        label = {
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = if (category in selectedValues) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else { null },
    )
}