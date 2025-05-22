package ru.mareanexx.feature_auth.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.data.remote.common.BaseResult
import ru.mareanexx.common.data.remote.common.Error
import ru.mareanexx.feature_auth.data.remote.dto.RegisterRequest
import ru.mareanexx.feature_auth.domain.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(registerRequest: RegisterRequest): Flow<BaseResult<Unit, Error>> {
        return authRepository.register(registerRequest)
    }
}