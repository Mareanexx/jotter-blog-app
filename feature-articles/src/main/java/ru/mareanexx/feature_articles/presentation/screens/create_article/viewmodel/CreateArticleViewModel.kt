package ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
import ru.mareanexx.feature_articles.data.mapper.toRequest
import ru.mareanexx.feature_articles.data.remote.dto.Category
import ru.mareanexx.feature_articles.domain.usecase.CreateArticleUseCase
import ru.mareanexx.feature_articles.domain.usecase.GetCategoriesUseCase
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.event.CreateArticleEvent
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.form.CreateArticleForm
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.ArticleField
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.CategoriesUiState
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.CreateArticleUiState
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.ValidationErrorType
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateArticleViewModel @Inject constructor(
    private val createArticleUseCase: CreateArticleUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private val _newArticleForm = MutableStateFlow(CreateArticleForm())
    val newArticleForm = _newArticleForm.asStateFlow()

    private val _allCategories = MutableStateFlow(emptyList<Category>())

    private val _categoriesUiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.LoadingCategories)
    val categoriesUiState = _categoriesUiState.asStateFlow()

    private val _articleUiState = MutableStateFlow<CreateArticleUiState>(CreateArticleUiState.Showing)
    val articleUiState = _articleUiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<CreateArticleEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init { getCategories() }

    // events
    private fun showToast(message: String?) { viewModelScope.launch { _eventFlow.emit(CreateArticleEvent.ShowToast(message ?: "Unknown error")) } }
    private fun setNextPage() { viewModelScope.launch { _eventFlow.emit(CreateArticleEvent.NextPage) } }
    private fun showSuccessDialog() { viewModelScope.launch { _eventFlow.emit(CreateArticleEvent.ShowSuccessDialog) } }

    // states
    private fun setLoadingCategoriesState() { _categoriesUiState.value = CategoriesUiState.LoadingCategories }
    private fun setShowingSuccessState() { _categoriesUiState.value = CategoriesUiState.Showing }
    private fun setErrorLoadingCategories() { _categoriesUiState.value = CategoriesUiState.ErrorLoadingCategories }
    private fun setProcessingCreation() { _articleUiState.value = CreateArticleUiState.ProcessingCreation }
    private fun setErrorCreatingArticleState() { _articleUiState.value = CreateArticleUiState.ErrorCreatingArticle }
    private fun setShowingArticleState() { _articleUiState.value = CreateArticleUiState.Showing }
    private fun setValidationError(fieldsError: Map<ArticleField, ValidationErrorType>) { _newArticleForm.value = _newArticleForm.value.copy(fieldError = fieldsError) }
    fun removeValidationError(key: ArticleField) {
        if (_newArticleForm.value.fieldError.isNotEmpty()) {
            val fieldErrors: MutableMap<ArticleField, ValidationErrorType> = _newArticleForm.value.fieldError.toMutableMap()
            fieldErrors.remove(key)
            setValidationError(fieldErrors)
        }
    }

    // validation
    private fun validateArticleName(): Boolean = _newArticleForm.value.title.length >= 6
    private fun validateArticleContent(): Boolean = _newArticleForm.value.content.value.annotatedString.length >= 6
    private fun validateSelectedCategory(): Boolean = _newArticleForm.value.selectedCategories.isNotEmpty()

    private fun firstValidation(): Boolean {
        val fieldErrors = mutableMapOf<ArticleField, ValidationErrorType>()
        if (!validateArticleName()) fieldErrors[ArticleField.Name] = ValidationErrorType.TOO_SHORT_NAME
        if (!validateSelectedCategory()) fieldErrors[ArticleField.Categories] = ValidationErrorType.NO_CATEGORIES

        return if (fieldErrors.isEmpty()) { true } else { setValidationError(fieldErrors); false }
    }

    private fun secondValidation(): Boolean {
        val fieldErrors = mutableMapOf<ArticleField, ValidationErrorType>()
        if (!validateArticleContent()) fieldErrors[ArticleField.Content] = ValidationErrorType.NO_CONTENT

        return if (fieldErrors.isEmpty()) { true } else { setValidationError(fieldErrors); false }
    }

    // onChange funs
    fun onArticleNameChanged(newValue: String) { _newArticleForm.value = _newArticleForm.value.copy(title = newValue); removeValidationError(ArticleField.Name) }

    fun onCategorySearchChanged(newValue: String) {
        _newArticleForm.value = _newArticleForm.value.copy(
            categorySearch = newValue,
            shownCategories = if (newValue.isBlank()) {
                _allCategories.value
            } else {
                _allCategories.value.filter { newValue.lowercase() in it.name.lowercase() }
            }
        )
    }

    fun onCoverImageChanged(newImage: File) { _newArticleForm.value = _newArticleForm.value.copy(photo = newImage) }

    fun onAddNewCategory(newCategory: Category) {
        _newArticleForm.value = _newArticleForm.value.copy(
            selectedCategories = _newArticleForm.value.selectedCategories + newCategory,
            shownCategories = _newArticleForm.value.shownCategories - newCategory
        )
        _allCategories.value -= newCategory
        removeValidationError(key = ArticleField.Categories)
    }

    fun onRemoveCategory(removedCategory: Category) {
        _newArticleForm.value = _newArticleForm.value.copy(
            selectedCategories = _newArticleForm.value.selectedCategories - removedCategory,
            shownCategories = _newArticleForm.value.shownCategories + removedCategory
        )
        _allCategories.value += removedCategory
    }

    // Article content changers
    fun onBoldApply() { _newArticleForm.value.content.value.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) }
    fun onItalicApply() { _newArticleForm.value.content.value.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic)) }
    fun onUnderlineApply() { _newArticleForm.value.content.value.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.combine(listOf(TextDecoration.Underline)))) }
    fun onStrikethroughApply() { _newArticleForm.value.content.value.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.combine(listOf(TextDecoration.LineThrough)))) }

    private fun getMarkdown() = _newArticleForm.value.content.value.toMarkdown()

    // button clicked
    fun onNextClicked() { if (firstValidation()) setNextPage() }

    // working with use cases
    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .onStart { setLoadingCategoriesState() }
                .catch { exception ->
                    showToast(exception.localizedMessage)
                    setErrorLoadingCategories()
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorLoadingCategories() }
                        is BaseResult.Success -> {
                            _allCategories.value = baseResult.data
                            _newArticleForm.value = _newArticleForm.value.copy(
                                shownCategories = baseResult.data
                            )
                            setShowingSuccessState()
                        }
                    }
                }
        }
    }

    fun createArticle() {
        viewModelScope.launch {
            if (!secondValidation()) return@launch
            createArticleUseCase(_newArticleForm.value.toRequest(getMarkdown()), _newArticleForm.value.photo)
                .onStart { setProcessingCreation() }
                .catch { exception -> showToast(exception.localizedMessage); setErrorCreatingArticleState() }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorCreatingArticleState() }
                        is BaseResult.Success -> {
                            setShowingArticleState()
                            showSuccessDialog()
                        }
                    }
                }
        }
    }
}