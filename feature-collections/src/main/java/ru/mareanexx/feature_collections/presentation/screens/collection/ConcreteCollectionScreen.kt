package ru.mareanexx.feature_collections.presentation.screens.collection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.common.components.ErrorRetry
import ru.mareanexx.common.ui.common.components.SmallScreenHeader
import ru.mareanexx.feature_collections.presentation.components.CollectionEventHandler
import ru.mareanexx.feature_collections.presentation.screens.collection.components.CollectionArticleCard
import ru.mareanexx.feature_collections.presentation.screens.collection.components.CollectionSettingsButton
import ru.mareanexx.feature_collections.presentation.screens.collection.components.ConcreteCollectionSkeleton
import ru.mareanexx.feature_collections.presentation.screens.collection.viewmodel.state.ConcreteCollectionUiState
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.CollectionsViewModel
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.event.BottomSheetType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConcreteCollectionScreen(
    collectionName: String, collectionId: Int,
    onNavigateBack: () -> Unit,
    collectionsViewModel: CollectionsViewModel
) {
    val collectionArticles by collectionsViewModel.collectionArticles.collectAsState()
    val uiState = collectionsViewModel.articlesUiState.collectAsState()

    CollectionEventHandler(
        eventFlow = collectionsViewModel.eventFlow,
        onDeleteConfirmed = { collectionsViewModel.deleteCollection(collectionId); onNavigateBack() },
        onUpdateCollectionName = { collectionsViewModel.updateCollectionName(collectionId) },
        onClearForm = { collectionsViewModel.clearForm() },
        collectionsViewModel = collectionsViewModel
    )

    when(uiState.value) {
        ConcreteCollectionUiState.Error -> { ErrorRetry(onRetry = { collectionsViewModel.retryArticles(collectionId) }) }
        ConcreteCollectionUiState.Loading -> { ConcreteCollectionSkeleton() }
        ConcreteCollectionUiState.Showing -> {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                stickyHeader {
                    SmallScreenHeader(title = collectionName, onNavigateBack = onNavigateBack,
                        rightContent = {
                            CollectionSettingsButton(
                                onDeleteCollection = { collectionsViewModel.showDeleteConfirmationDialog() },
                                onRenameCollection = {
                                    collectionsViewModel.showTypifiedBottomSheet(BottomSheetType.Update, collectionName, collectionId)
                                }
                            )
                        }
                    )
                }

                item { Spacer(Modifier.height(15.dp)) }

                items(collectionArticles) { collectionArticle ->
                    CollectionArticleCard(
                        collectionArticle = collectionArticle,
                        onRemoveFromCollection = { article -> collectionsViewModel.deleteArticleFromCollection(article) }
                    )
                }
            }
        }
    }
}