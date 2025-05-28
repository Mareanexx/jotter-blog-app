package ru.mareanexx.common.ui.common.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.core.common.R

@Composable
fun CustomTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    @DrawableRes icon: Int? = null,
    isPassword: Boolean = false,
    isError: Boolean = false,
    @StringRes errorRes: Int,
    keyboardType: KeyboardType, imeAction: ImeAction
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(
            text = stringResource(label), style = MaterialTheme.typography.labelSmall,
            color = if (!isError) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = 50.dp)
                .background(
                    color = if (!isError) LightGray.copy(alpha = 0.15f) else MaterialTheme.colorScheme.error,
                    shape = Shapes.medium
                )
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(it),
                    tint = if (!isError) MaterialTheme.colorScheme.surfaceBright else MaterialTheme.colorScheme.onError,
                    contentDescription = stringResource(placeholder)
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChanged,
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
                cursorBrush = SolidColor(value = MaterialTheme.colorScheme.onSurface),
                visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = if (!isError) MaterialTheme.colorScheme.surfaceBright else MaterialTheme.colorScheme.onError),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty()) {
                            Text(
                                text = stringResource(placeholder),
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (!isError) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onError
                            )
                        }
                        innerTextField()
                    }
                }
            )
            if (isPassword) {
                val visibilityIconRes = if (isPasswordVisible) R.drawable.visibility_off_icon else R.drawable.visibility_icon
                Icon(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { isPasswordVisible = !isPasswordVisible },
                    painter = painterResource(visibilityIconRes),
                    tint = MaterialTheme.colorScheme.surfaceBright,
                    contentDescription = null
                )
            }
        }
        if (isError) { ErrorSub(errorRes) }
    }
}

@Composable
fun ErrorSub(@StringRes errorRes: Int) {
    Row(
        modifier = Modifier.padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            modifier = Modifier.size(17.dp),
            painter = painterResource(R.drawable.error_icon),
            contentDescription = null, tint = MaterialTheme.colorScheme.error,
        )
        Text(
            text = stringResource(errorRes),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall
        )
    }
}