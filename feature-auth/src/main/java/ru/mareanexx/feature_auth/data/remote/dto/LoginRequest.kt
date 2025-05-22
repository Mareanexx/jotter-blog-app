package ru.mareanexx.feature_auth.data.remote.dto

data class LoginRequest(
    val email: String,
    val password: String
)