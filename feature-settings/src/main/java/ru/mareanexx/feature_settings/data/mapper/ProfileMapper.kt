package ru.mareanexx.feature_settings.data.mapper

import ru.mareanexx.data.profile.entity.ProfileEntity
import ru.mareanexx.feature_settings.data.remote.dto.UpdateProfileRequest
import ru.mareanexx.feature_settings.domain.entity.Profile
import ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.form.ProfileForm

fun ProfileForm.toRequest(id: Int) = UpdateProfileRequest(
    id = id,
    bio = bio.ifBlank { null },
    email = email.ifBlank { null },
    username = username,
    birthdate = birthdate
)

fun ProfileEntity.toDto() = Profile(
    id = id,
    email = email,
    username = username,
    birthdate = birthdate,
    bio = bio,
    avatar = avatar,
    userUuid = userUuid,
    createdAt = createdAt
)

fun Profile.toEntity() = ProfileEntity(
    id = id,
    email = email,
    username = username,
    avatar = avatar,
    birthdate = birthdate,
    bio = bio,
    userUuid = userUuid,
    createdAt = createdAt
)

fun Profile.toForm() = ProfileForm(
    id = id,
    username = username,
    email = email,
    createdAt = createdAt,
    avatarPath = avatar ?: "",
    bio = bio ?: "",
    birthdate = birthdate
)