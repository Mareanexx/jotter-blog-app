package ru.mareanexx.core.data.common

sealed class BaseResult<out D : Any, out E : Any> {
    data class Success<D : Any>(val data: D) : BaseResult<D, Nothing>()
    data class Error<E : Any>(val error: E) : BaseResult<Nothing, E>()
}