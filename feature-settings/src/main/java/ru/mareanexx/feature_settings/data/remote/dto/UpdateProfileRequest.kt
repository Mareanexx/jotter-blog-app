package ru.mareanexx.feature_settings.data.remote.dto

import java.time.LocalDate

data class UpdateProfileRequest(
    val id: Int,
    val bio: String? = null,
    val email: String? = null,
    val username: String? = null,
    val birthdate: LocalDate? = null
)