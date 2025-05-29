package ru.mareanexx.feature_articles.presentation.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.common.components.CustomPullToRefreshBox
import ru.mareanexx.common.ui.common.components.ErrorRetry
import ru.mareanexx.common.ui.common.components.HeaderRowIcons
import ru.mareanexx.feature_articles.R
import ru.mareanexx.feature_articles.presentation.components.skeleton.CategoriesSkeleton
import ru.mareanexx.feature_articles.presentation.components.skeleton.DiscoverSkeleton
import ru.mareanexx.feature_articles.presentation.screens.discover.components.AnimatedCategoryNameHeader
import ru.mareanexx.feature_articles.presentation.screens.discover.components.ArticleCard
import ru.mareanexx.feature_articles.presentation.screens.discover.components.CategoryChip
import ru.mareanexx.feature_articles.presentation.screens.discover.viewmodel.DiscoverViewModel
import ru.mareanexx.feature_articles.presentation.screens.discover.viewmodel.state.DiscoverUiState

enum class DiscoverRoute(val route: String) {
    Search(route = "discover/search")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    onNavigateToSearchScreen: () -> Unit,
    discoverViewModel: DiscoverViewModel = hiltViewModel()
) {
    val gridState = rememberLazyStaggeredGridState()

    val isRefreshing = discoverViewModel.isRefreshing.collectAsState()
    val categoryArticles = discoverViewModel.categoriesArticles.collectAsState()
    val selectedCategory = discoverViewModel.selectedCategory.collectAsState()
    val isLoadingArticles = discoverViewModel.isLoadingArticles.collectAsState()
    val uiState = discoverViewModel.uiState.collectAsState()

    LaunchedEffect(selectedCategory.value) {
        if (categoryArticles.value.firstOrNull {
                it.category.id == (selectedCategory.value?.category?.id ?: -1)
            }?.articles?.isEmpty() == true) {
            discoverViewModel.loadCategoryArticles(selectedCategory.value?.category)
        }
    }

    val showHeader = remember { derivedStateOf { gridState.firstVisibleItemIndex > 0 } }

    Scaffold(
        topBar = {
            if (showHeader.value) {
                AnimatedCategoryNameHeader(
                    showHeader = showHeader.value,
                    selectedCategory = selectedCategory,
                    onNavigateToSearchScreen = onNavigateToSearchScreen
                )
            }
        }
    ) { padding ->
        CustomPullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = { discoverViewModel.refresh() }
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                state = gridState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp),
                verticalItemSpacing = 15.dp,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    HeaderRowIcons(
                        onSearchClicked = onNavigateToSearchScreen, bottomPadding = 0.dp,
                        middleText = {
                            Text(
                                text = stringResource(R.string.discover_title),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    )
                }
                item(span = StaggeredGridItemSpan.FullLine) {
                    when(uiState.value) {
                        DiscoverUiState.Error -> { ErrorRetry(onRetry = { discoverViewModel.retry() }) }
                        DiscoverUiState.Loading -> { CategoriesSkeleton() }
                        DiscoverUiState.Success -> {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                items(categoryArticles.value) { category ->
                                    CategoryChip(
                                        selectedCategory = selectedCategory.value,
                                        category = category,
                                        onChangeSelectedCategory = { discoverViewModel.onChangeSelectedCategory(category) }
                                    )
                                }
                            }
                        }
                    }
                }

                when(isLoadingArticles.value) {
                    true -> { item(span = StaggeredGridItemSpan.FullLine) { DiscoverSkeleton() } }
                    false -> {
                        items(selectedCategory.value?.articles ?: emptyList()) { selected ->
                            ArticleCard(selected)
                        }
                    }
                }
            }
        }
    }
}