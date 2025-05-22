package ru.mareanexx.feature_auth.domain

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_auth.data.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.data.remote.dto.RegisterRequest

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest) : Flow<BaseResult<Unit, Error>>
    suspend fun register(registerRequest: RegisterRequest) : Flow<BaseResult<Unit, Error>>
}