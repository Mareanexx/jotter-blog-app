package ru.mareanexx.feature_auth.data.remote.dto

import java.time.LocalDate

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val birthdate: LocalDate?
)