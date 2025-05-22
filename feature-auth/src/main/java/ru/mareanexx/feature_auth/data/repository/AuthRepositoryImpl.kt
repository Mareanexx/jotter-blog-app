package ru.mareanexx.feature_auth.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.core.data.common.BaseResult
import ru.mareanexx.core.data.common.Error
import ru.mareanexx.core.utils.UserSessionManager
import ru.mareanexx.feature_auth.data.remote.api.AuthApi
import ru.mareanexx.feature_auth.data.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.data.remote.dto.RegisterRequest
import ru.mareanexx.feature_auth.domain.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<Unit, Error>> {
        return flow {
            val response = authApi.login(loginRequest)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                userSessionManager.saveSession(data.accessToken, data.userUuid)
                emit(BaseResult.Success(Unit))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.body()?.message)))
            }
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<Unit, Error>> {
        return flow {
            val response = authApi.register(registerRequest)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                userSessionManager.saveSession(data.accessToken, data.userUuid)
                emit(BaseResult.Success(Unit))
            } else {
                emit(BaseResult.Error(Error(code = response.code(), message = response.body()?.message)))
            }
        }
    }
}