package ru.mareanexx.common.ui.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.mareanexx.core.common.R

@Composable
fun AuthScreenHeader(
    @StringRes title: Int,
    @StringRes description: Int,
    paddingTop: Dp = 40.dp,
    onNavigateBack: (() -> Unit)? = null
) {
    Spacer(modifier = Modifier.height(paddingTop))
    onNavigateBack?.let {
        NavigateBackButton(onNavigateBack = onNavigateBack)
        Spacer(modifier = Modifier.height(40.dp))
    }

    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = stringResource(title),
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Text(
        text = stringResource(description),
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(paddingTop + 20.dp))
}

@Composable
fun HeaderRowIcons(
    onSearchClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(R.drawable.jotter_light_theme_icon),
            contentDescription = null,
        )
        Icon(
            modifier = Modifier.size(28.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onSearchClicked() },
            painter = painterResource(R.drawable.search_icon),
            contentDescription = null, tint = MaterialTheme.colorScheme.onBackground
        )
    }
}