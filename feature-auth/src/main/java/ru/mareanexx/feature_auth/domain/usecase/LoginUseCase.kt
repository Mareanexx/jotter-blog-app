package ru.mareanexx.feature_auth.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.core.data.common.BaseResult
import ru.mareanexx.core.data.common.Error
import ru.mareanexx.feature_auth.data.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.domain.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): Flow<BaseResult<Unit, Error>> {
        return authRepository.login(loginRequest)
    }
}