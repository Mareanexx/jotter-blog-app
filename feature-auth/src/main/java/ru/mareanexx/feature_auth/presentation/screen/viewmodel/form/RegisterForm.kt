package ru.mareanexx.feature_auth.presentation.screen.viewmodel.form

import java.time.LocalDate

data class RegisterForm(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val birthdate: LocalDate? = null
)