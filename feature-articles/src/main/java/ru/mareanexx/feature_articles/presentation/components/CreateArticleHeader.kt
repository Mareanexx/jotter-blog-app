package ru.mareanexx.feature_articles.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun CreateArticleHeader(
    @StringRes title: Int,
    @DrawableRes backIcon: (Int)? = null,
    onNavigateBack: () -> Unit = {},
    onCloseClicked: () -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        backIcon?.let {
            Icon(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null, onClick = onNavigateBack
                ),
                painter = painterResource(ru.mareanexx.core.common.R.drawable.arrow_left_icon),
                contentDescription = stringResource(ru.mareanexx.core.common.R.string.navigate_back_dsc),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, onClick = onCloseClicked
            ),
            painter = painterResource(ru.mareanexx.core.common.R.drawable.close_icon),
            contentDescription = stringResource(ru.mareanexx.core.common.R.string.navigate_back_dsc),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}