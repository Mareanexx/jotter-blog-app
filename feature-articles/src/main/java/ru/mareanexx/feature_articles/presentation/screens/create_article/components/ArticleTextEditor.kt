package ru.mareanexx.feature_articles.presentation.screens.create_article.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import ru.mareanexx.common.ui.common.components.ErrorSub
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.feature_articles.R
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.form.CreateArticleForm
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.state.ValidationErrorType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomRichTextEditor(
    newArticleForm: State<CreateArticleForm>,
    weightModifier: Modifier,
    contentError: ValidationErrorType?
) {
    RichTextEditor(
        modifier = weightModifier.fillMaxWidth(),
        state = newArticleForm.value.content.value,
        shape = Shapes.medium,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
        placeholder = {
            Text(
                text = stringResource(R.string.start_typing_here_placeholder),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        },
        supportingText = { if (contentError != null) ErrorSub(errorRes = contentError.errorText) },
        isError = contentError != null,
        colors = RichTextEditorDefaults.richTextEditorColors(
            containerColor = LightGray.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}