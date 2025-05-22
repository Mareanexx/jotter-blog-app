package ru.mareanexx.feature_auth.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.mareanexx.common.data.remote.common.WrappedResponse
import ru.mareanexx.feature_auth.data.remote.dto.AuthResponse
import ru.mareanexx.feature_auth.data.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.data.remote.dto.RegisterRequest

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<WrappedResponse<AuthResponse>>

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest) : Response<WrappedResponse<AuthResponse>>
}