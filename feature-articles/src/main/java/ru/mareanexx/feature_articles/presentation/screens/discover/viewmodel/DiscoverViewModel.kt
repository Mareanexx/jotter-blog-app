package ru.mareanexx.feature_articles.presentation.screens.discover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.data.remote.dto.CategoryArticles
import ru.mareanexx.feature_articles.domain.usecase.GetArticlesByCategoryUseCase
import ru.mareanexx.feature_articles.domain.usecase.GetFirstCategoryArticlesUseCase
import ru.mareanexx.feature_articles.presentation.screens.discover.viewmodel.state.DiscoverUiState
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getFirstCategoryArticlesUseCase: GetFirstCategoryArticlesUseCase,
    private val getArticlesByCategoryUseCase: GetArticlesByCategoryUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    private val _categoriesArticles = MutableStateFlow<List<CategoryArticles>>(emptyList())
    val categoriesArticles get() = _categoriesArticles.asStateFlow()

    private val _selectedCategory = MutableStateFlow<CategoryArticles?>(null)
    val selectedCategory get() = _selectedCategory.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    private val _isLoadingArticles = MutableStateFlow(false)
    val isLoadingArticles get() = _isLoadingArticles.asStateFlow()

    init { firstLoadCategories() }

    // states
    private fun setLoadingState() { _uiState.value = DiscoverUiState.Loading; setLoadingArticles(true) }
    private fun setSuccessState() { _uiState.value = DiscoverUiState.Success }
    private fun setErrorState() { _uiState.value = DiscoverUiState.Error }
    private fun setLoadingArticles(state: Boolean) { _isLoadingArticles.value = state }

    // re-smth
    fun retry() { firstLoadCategories() }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadCategoryArticles(_selectedCategory.value?.category)
            _isRefreshing.value = false
        }
    }

    // onChange
    fun onChangeSelectedCategory(categoryArticles: CategoryArticles?) {
        _selectedCategory.value = categoryArticles
    }

    private fun firstLoadCategories() {
        viewModelScope.launch {
            getFirstCategoryArticlesUseCase()
                .onStart { setLoadingState() }
                .catch { setErrorState(); setLoadingArticles(false) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorState(); setLoadingArticles(false) }
                        is BaseResult.Success -> {
                            _categoriesArticles.value = baseResult.data
                            _selectedCategory.value = baseResult.data.firstOrNull()
                            setSuccessState()
                        }
                    }
                }
        }
    }

    fun loadCategoryArticles(category: Category?) {
        if (category == null) return
        viewModelScope.launch {
            getArticlesByCategoryUseCase(category)
                .onStart { setLoadingArticles(true) }
                .catch { setErrorState(); setLoadingArticles(false) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorState();setLoadingArticles(false) }
                        is BaseResult.Success -> {
                            _selectedCategory.value = baseResult.data
                            _categoriesArticles.value = _categoriesArticles.value.map {
                                if (it.category == category) baseResult.data else it
                            }
                            setLoadingArticles(false)
                        }
                    }
                }
        }
    }
}