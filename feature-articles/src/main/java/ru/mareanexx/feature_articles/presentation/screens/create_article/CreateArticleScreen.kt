package ru.mareanexx.feature_articles.presentation.screens.create_article

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.common.components.CustomTextField
import ru.mareanexx.common.utils.FileUtils.uriToFile
import ru.mareanexx.feature_articles.R
import ru.mareanexx.feature_articles.presentation.components.BottomButtonAndStepNumber
import ru.mareanexx.feature_articles.presentation.components.CreateArticleHeader
import ru.mareanexx.feature_articles.presentation.screens.create_article.components.CategoriesChooserRow
import ru.mareanexx.feature_articles.presentation.screens.create_article.components.CoverImage
import ru.mareanexx.feature_articles.presentation.screens.create_article.components.CreateArticleEventHandler
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.CreateArticleViewModel
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.ArticleField

enum class CreateArticleRoute(val route: String) {
    AddContent("add_article_content")
}

@Composable
fun CreateArticleScreen(
    onNavigateBack: () -> Unit,
    onNavigateToNextPage: () -> Unit,
    viewModel: CreateArticleViewModel
) {
    val context = LocalContext.current
    val newArticleForm = viewModel.newArticleForm.collectAsState()
    val uiState = viewModel.categoriesUiState.collectAsState()

    val nameError = newArticleForm.value.fieldError[ArticleField.Name]

    CreateArticleEventHandler(
        eventFlow = viewModel.eventFlow,
        onNavigateNextPage = onNavigateToNextPage
    )

    val coverPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> uri?.let { viewModel.onCoverImageChanged(uriToFile(uri, context)) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 20.dp, bottom = 30.dp)
            .padding(horizontal = 20.dp)
    ) {
        CreateArticleHeader(title = R.string.create_article_label, onCloseClicked = onNavigateBack)

        Spacer(modifier = Modifier.height(25.dp))

        CoverImage(
            coverPhoto = newArticleForm.value.photo,
            onUploadCoverPhoto = { coverPhotoLauncher.launch("image/*") }
        )

        Spacer(modifier = Modifier.height(25.dp))

        CustomTextField(
            value = newArticleForm.value.title,
            onValueChanged = { viewModel.onArticleNameChanged(it) },
            label = R.string.tf_article_name_label,
            placeholder = R.string.tf_article_name_placeholder,
            isError = nameError != null,
            errorRes = nameError?.errorText ?: R.string.empty_string,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(15.dp))

        CategoriesChooserRow(
            uiState = uiState,
            newArticleForm = newArticleForm,
            onSearchChanged = { viewModel.onCategorySearchChanged(it) },
            onRemoveFromSelected = { viewModel.onRemoveCategory(it) },
            onAddToSelected = { viewModel.onAddNewCategory(it) }
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomButtonAndStepNumber(
            isLoading = false,
            stepNumber = 1,
            buttonText = R.string.next_step_btn,
            onButtonClicked = { viewModel.onNextClicked() }
        )
    }
}