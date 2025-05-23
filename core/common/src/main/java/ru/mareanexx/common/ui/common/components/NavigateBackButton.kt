package ru.mareanexx.common.ui.common.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mareanexx.core.common.R

@Composable
fun NavigateBackButton(onNavigateBack: () -> Unit) {
    IconButton(onClick = onNavigateBack) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(R.drawable.arrow_left_icon),
            contentDescription = stringResource(R.string.navigate_back_dsc),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewButton() {
    NavigateBackButton {  }
}