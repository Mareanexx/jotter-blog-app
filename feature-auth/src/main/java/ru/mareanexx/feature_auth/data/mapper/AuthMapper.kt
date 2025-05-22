package ru.mareanexx.feature_auth.data.mapper

import ru.mareanexx.feature_auth.data.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.data.remote.dto.RegisterRequest
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.form.LoginForm
import ru.mareanexx.feature_auth.presentation.screen.viewmodel.form.RegisterForm

fun LoginForm.toRequest() = LoginRequest(
    email = email,
    password = password
)

fun RegisterForm.toRequest() = RegisterRequest(
    email = email,
    password = password,
    username = username,
    birthdate = birthdate
)