package ru.mareanexx.feature_settings.presentation.screens.profile.viewmodel.form

import java.io.File
import java.time.LocalDate
import java.time.OffsetDateTime

data class ProfileForm(
    val id: Int = -1,
    val email: String = "",
    val username: String = "",
    val bio: String = "",
    val avatarPath: String = "",
    val avatarFile: File? = null,
    val birthdate: LocalDate? = null,
    val createdAt: OffsetDateTime? = null
)