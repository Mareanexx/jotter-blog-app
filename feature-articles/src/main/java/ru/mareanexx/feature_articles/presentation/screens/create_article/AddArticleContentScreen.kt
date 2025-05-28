package ru.mareanexx.feature_articles.presentation.screens.create_article

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ru.mareanexx.feature_articles.R
import ru.mareanexx.feature_articles.presentation.components.BottomButtonAndStepNumber
import ru.mareanexx.feature_articles.presentation.components.CreateArticleHeader
import ru.mareanexx.feature_articles.presentation.screens.create_article.components.CreateArticleEventHandler
import ru.mareanexx.feature_articles.presentation.screens.create_article.components.CustomRichTextEditor
import ru.mareanexx.feature_articles.presentation.screens.create_article.components.StyleButton
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.CreateArticleViewModel
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.ArticleField
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.CreateArticleUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddArticleContentScreen(
    onNavigateBack: () -> Unit,
    onCloseClicked: () -> Unit,
    viewModel: CreateArticleViewModel
) {
    val newArticleForm = viewModel.newArticleForm.collectAsState()
    val uiState = viewModel.articleUiState.collectAsState()

    CreateArticleEventHandler(viewModel.eventFlow, onClosedClicked = onCloseClicked)

    LaunchedEffect(newArticleForm.value.content.value) { viewModel.removeValidationError(ArticleField.Content) }

    val isBold: Boolean = newArticleForm.value.content.value.currentSpanStyle.fontWeight == FontWeight.Bold
    val isItalic: Boolean = newArticleForm.value.content.value.currentSpanStyle.fontStyle == FontStyle.Italic
    val isUnderlined: Boolean = newArticleForm.value.content.value.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) ?: false
    val isStrikethrough: Boolean = newArticleForm.value.content.value.currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) ?: false

    val contentError = newArticleForm.value.fieldError[ArticleField.Content]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        CreateArticleHeader(
            title = R.string.add_content_title,
            backIcon = ru.mareanexx.core.common.R.drawable.arrow_left_icon,
            onNavigateBack = onNavigateBack,
            onCloseClicked = onCloseClicked
        )

        Spacer(modifier = Modifier.height(25.dp))

        CustomRichTextEditor(
            newArticleForm = newArticleForm,
            weightModifier = Modifier.weight(1f),
            contentError = contentError
        )

        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .imePadding(),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            StyleButton(
                icon = R.drawable.format_bold_icon,
                contentDescription = R.string.cd_action_set_bold_text_style,
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                isStyled = isBold,
                onChangeStyle = { viewModel.onBoldApply() }
            )
            StyleButton(
                icon = R.drawable.format_italic_icon,
                contentDescription = R.string.cd_action_set_italic_text_style,
                isStyled = isItalic,
                onChangeStyle = { viewModel.onItalicApply() }
            )
            StyleButton(
                icon = R.drawable.format_strikethrough_icon,
                contentDescription = R.string.cd_action_set_strikethrough_text_style,
                isStyled = isStrikethrough,
                onChangeStyle = { viewModel.onStrikethroughApply() }
            )
            StyleButton(
                icon = R.drawable.format_underlined_icon,
                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                contentDescription = R.string.cd_action_set_underlined_text_style,
                isStyled = isUnderlined,
                onChangeStyle = { viewModel.onUnderlineApply() }
            )
        }

        BottomButtonAndStepNumber(
            isLoading = uiState.value == CreateArticleUiState.ProcessingCreation,
            stepNumber = 2,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            buttonText = R.string.create_article_label,
            onButtonClicked = { viewModel.createArticle() }
        )
    }
}