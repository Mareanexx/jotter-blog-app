package ru.mareanexx.feature_collections.presentation.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.common.components.CustomPullToRefreshBox
import ru.mareanexx.common.ui.common.components.ErrorRetry
import ru.mareanexx.common.ui.common.components.HeaderRowIcons
import ru.mareanexx.common.ui.common.components.ScreenHeader
import ru.mareanexx.feature_collections.R
import ru.mareanexx.feature_collections.presentation.components.CollectionEventHandler
import ru.mareanexx.feature_collections.presentation.screens.list.components.AddNewCollectionButton
import ru.mareanexx.feature_collections.presentation.screens.list.components.CollectionCard
import ru.mareanexx.feature_collections.presentation.screens.list.components.CollectionsSkeleton
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.CollectionsViewModel
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.event.BottomSheetType
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.state.CollectionsUiState

enum class CollectionsRoute(val route: String) {
    CollectionArticles("collection_articles")
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CollectionsScreen(
    onNavigateToConcreteCollection: (collectionId: Int, collectionName: String) -> Unit,
    collectionsViewModel: CollectionsViewModel
) {
    val collectionUiState = collectionsViewModel.collectionsUiState.collectAsState()
    val collectionsData  = collectionsViewModel.collectionsData.collectAsState()
    val isRefreshing = collectionsViewModel.isRefreshing.collectAsState()

    CollectionEventHandler(
        eventFlow = collectionsViewModel.eventFlow,
        onAddNewCollection = { collectionsViewModel.addNewCollection() },
        onClearForm = { collectionsViewModel.clearForm() },
        collectionsViewModel = collectionsViewModel
    )

    CustomPullToRefreshBox(
        isRefreshing = isRefreshing.value,
        onRefresh = { collectionsViewModel.refresh() }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {

            item { HeaderRowIcons(onSearchClicked = { TODO() }) }

            stickyHeader {
                Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background).padding(top = 15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        ScreenHeader(title = R.string.collections, description = R.string.collections_header_description, paddingTop = 0.dp)
                    }
                    AddNewCollectionButton(
                        onAddNewCollection = {
                            collectionsViewModel.showTypifiedBottomSheet(BottomSheetType.Create)
                        }
                    )
                }
            }

            when(collectionUiState.value) {
                CollectionsUiState.Error -> { item { ErrorRetry(onRetry = { collectionsViewModel.retryCollections() }) } }
                CollectionsUiState.Loading -> { item { CollectionsSkeleton() } }
                CollectionsUiState.Showing -> {
                    items(collectionsData.value) { collection ->
                        CollectionCard(
                            collection = collection,
                            onNavigateToConcreteCollection = onNavigateToConcreteCollection,
                            onGetArticles = { collectionsViewModel.getArticles(collection.collection.id) }
                        )
                    }
                }
            }
        }
    }
}