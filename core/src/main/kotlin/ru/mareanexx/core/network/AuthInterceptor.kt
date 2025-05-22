package ru.mareanexx.core.network

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.mareanexx.core.utils.UserSessionManager
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userSessionManager: UserSessionManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            userSessionManager.getToken()
        }

        val newRequest = chain.request().newBuilder().apply {
            if (token.isNotEmpty()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()

        return chain.proceed(newRequest)
    }
}