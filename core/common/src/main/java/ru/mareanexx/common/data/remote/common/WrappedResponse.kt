package ru.mareanexx.common.data.remote.common

data class WrappedResponse<T>(
    val message: String? = null,
    val data: T? = null
)

data class Error(
    val code: Int? = null,
    val message: String? = "Unknown error"
)