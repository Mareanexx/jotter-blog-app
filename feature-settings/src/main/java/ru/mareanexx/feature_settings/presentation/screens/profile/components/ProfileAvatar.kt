package ru.mareanexx.feature_settings.presentation.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.common.utils.BuildConfig
import ru.mareanexx.feature_settings.R
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.form.ProfileForm

@Composable
fun ProfileAvatar(profileData: ProfileForm, onLaunchNewPhoto: () -> Unit) {
    Box {
        AsyncImage(
            modifier = Modifier.size(100.dp).clip(CircleShape),
            model = profileData.avatarFile ?: "${BuildConfig.API_FILES_URL}${profileData.avatarPath}",
            contentDescription = stringResource(R.string.user_avatar),
            placeholder = painterResource(R.drawable.avatar_placeholder),
            error = painterResource(R.drawable.avatar_placeholder),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.align(Alignment.BottomEnd).size(20.dp).clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.onBackground)
                .clickable { onLaunchNewPhoto() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(15.dp), tint = MaterialTheme.colorScheme.background,
                painter = painterResource(R.drawable.edit_icon),
                contentDescription = stringResource(R.string.edit_profile_avatar)
            )
        }
    }
}