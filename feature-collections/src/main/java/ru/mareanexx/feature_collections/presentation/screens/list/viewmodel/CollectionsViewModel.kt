package ru.mareanexx.feature_collections.presentation.screens.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.feature_collections.data.remote.dto.CollectionArticle
import ru.mareanexx.feature_collections.data.remote.dto.CollectionWithArticleImages
import ru.mareanexx.feature_collections.data.remote.dto.UpdateCollectionRequest
import ru.mareanexx.feature_collections.domain.usecase.CreateNewCollectionUseCase
import ru.mareanexx.feature_collections.domain.usecase.DeleteCollectionUseCase
import ru.mareanexx.feature_collections.domain.usecase.GetAllCollectionsUseCase
import ru.mareanexx.feature_collections.domain.usecase.GetCollectionArticlesUseCase
import ru.mareanexx.feature_collections.domain.usecase.RemoveArticleFromCollectionUseCase
import ru.mareanexx.feature_collections.domain.usecase.UpdateCollectionUseCase
import ru.mareanexx.feature_collections.presentation.screens.collection.viewmodel.state.ConcreteCollectionUiState
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.event.BottomSheetType
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.event.CollectionsEvent
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.form.NameValidationError
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.form.NewCollectionForm
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.state.CollectionsUiState
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val getAllCollectionsUseCase: GetAllCollectionsUseCase,
    private val createNewCollectionUseCase: CreateNewCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val getCollectionArticlesUseCase: GetCollectionArticlesUseCase,
    private val updateCollectionUseCase: UpdateCollectionUseCase,
    private val removeArticleFromCollectionUseCase: RemoveArticleFromCollectionUseCase
) : ViewModel() {

    private val _collectionsData = MutableStateFlow<List<CollectionWithArticleImages>>(emptyList())
    val collectionsData = _collectionsData.asStateFlow()

    private val _collectionArticles = MutableStateFlow<List<CollectionArticle>>(emptyList())
    val collectionArticles = _collectionArticles.asStateFlow()

    private val _newCollectionForm = MutableStateFlow(NewCollectionForm())
    val newCollectionForm = _newCollectionForm.asStateFlow()

    private val _collectionUiState = MutableStateFlow<CollectionsUiState>(CollectionsUiState.Loading)
    val collectionsUiState = _collectionUiState.asStateFlow()

    private val _articlesUiState = MutableStateFlow<ConcreteCollectionUiState>(ConcreteCollectionUiState.Loading)
    val articlesUiState = _articlesUiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<CollectionsEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init { getAllCollections() }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            getAllCollections()
            _isRefreshing.value = false
        }
    }
    fun retryCollections() = getAllCollections()
    fun retryArticles(collectionId: Int) = getArticles(collectionId)

    private fun setLoadingState() { _collectionUiState.value = CollectionsUiState.Loading }
    private fun setErrorState() { _collectionUiState.value = CollectionsUiState.Error }
    private fun setShowingState() { _collectionUiState.value = CollectionsUiState.Showing }
    fun showTypifiedBottomSheet(type: BottomSheetType, collectionName: String = "", collectionId: Int = -1) {
        viewModelScope.launch {
            if (type == BottomSheetType.Update) {
                _newCollectionForm.value = _newCollectionForm.value.copy(id = collectionId, name = collectionName)
            }
            _eventFlow.emit(CollectionsEvent.ShowBottomSheet(type))
        }
    }
    private fun showToast(message: String?) { viewModelScope.launch { _eventFlow.emit(CollectionsEvent.ShowToast(message ?: "Unknown error happened")) } }

    private fun validateName(): Boolean {
        if (_newCollectionForm.value.name.length < 3) {
            setValidationError(NameValidationError.TOO_SHORT_NAME)
            return false
        }
        if (_newCollectionForm.value.name.lowercase() in _collectionsData.value.map { it.collection.name.lowercase() }) {
            setValidationError(NameValidationError.SAME_NAME)
            return false
        }

        return true
    }

    fun clearForm() { _newCollectionForm.value = NewCollectionForm() }
    private fun setValidationError(newError: NameValidationError) {
        _newCollectionForm.value = _newCollectionForm.value.copy(nameValidationError = newError)
    }
    fun onCollectionNameChanged(newValue: String) { _newCollectionForm.value = _newCollectionForm.value.copy(name = newValue, nameValidationError = null) }

    private fun getAllCollections() {
        viewModelScope.launch {
            getAllCollectionsUseCase()
                .onStart { setLoadingState() }
                .catch { exception ->
                    exception.printStackTrace()
                    setErrorState()
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> setErrorState()
                        is BaseResult.Success -> {
                            _collectionsData.value = baseResult.data
                            setShowingState()
                        }
                    }
                }
        }
    }

    fun addNewCollection() {
        viewModelScope.launch {
            if (!validateName()) return@launch
            createNewCollectionUseCase(_newCollectionForm.value.name)
                .onStart { setLoadingState() }
                .catch {
                    exception -> exception.printStackTrace()
                    setValidationError(NameValidationError.CANT_ADD)
                    setShowingState()
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> {
                            setValidationError(NameValidationError.CANT_ADD)
                            setShowingState()
                        }
                        is BaseResult.Success -> {
                            _collectionsData.value = buildList {
                                add(0, CollectionWithArticleImages(
                                        collection = baseResult.data,
                                        previewPhotos = emptyList()
                                    )
                                )
                                addAll(_collectionsData.value)
                            }
                            showTypifiedBottomSheet(BottomSheetType.None)
                            setShowingState()
                        }
                    }
                }
        }
    }

    private fun setArticlesLoadingState() { _articlesUiState.value = ConcreteCollectionUiState.Loading }
    private fun setArticlesErrorState() { _articlesUiState.value = ConcreteCollectionUiState.Error }
    private fun setArticlesShowingState() { _articlesUiState.value = ConcreteCollectionUiState.Showing }
    fun showDeleteConfirmationDialog() {
        viewModelScope.launch {
            _eventFlow.emit(CollectionsEvent.ShowDeleteConfirmationDialog)
        }
    }

    fun getArticles(collectionId: Int) {
        viewModelScope.launch {
            getCollectionArticlesUseCase(collectionId)
                .onStart { setArticlesLoadingState() }
                .catch { setArticlesErrorState() }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setArticlesErrorState() }
                        is BaseResult.Success -> {
                            _collectionArticles.value = baseResult.data
                            setArticlesShowingState()
                        }
                    }
                }
        }
    }

    fun updateCollectionName(collectionId: Int) {
        viewModelScope.launch {
            if (!validateName()) return@launch
            updateCollectionUseCase(UpdateCollectionRequest(
                id = collectionId, name = _newCollectionForm.value.name
            ))
                .onStart { setLoadingState() }
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> {
                            setValidationError(NameValidationError.CANT_ADD)
                            setShowingState()
                        }
                        is BaseResult.Success -> {
                            _collectionsData.value = _collectionsData.value.map {
                                if (it.collection.id == collectionId) it.copy(collection = baseResult.data) else it
                            }
                            showTypifiedBottomSheet(BottomSheetType.None)
                            setShowingState()
                        }
                    }
                }
        }
    }

    fun deleteCollection(deletedCollectionId: Int) {
        viewModelScope.launch {
            deleteCollectionUseCase(deletedCollectionId)
                .onStart { setLoadingState() }
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error.message) }
                        is BaseResult.Success -> {
                            _collectionsData.value = _collectionsData.value.filter {
                                it.collection.id != deletedCollectionId
                            }
                            setShowingState()
                        }
                    }
                }
        }
    }

    fun deleteArticleFromCollection(article: CollectionArticle) {
        viewModelScope.launch {
            removeArticleFromCollectionUseCase(collectionId = article.collectionId, articleId = article.id)
                .catch { exception -> showToast(exception.localizedMessage) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> showToast(baseResult.error.message)
                        is BaseResult.Success -> { _collectionArticles.value -= article }
                    }
                }
        }
    }
}