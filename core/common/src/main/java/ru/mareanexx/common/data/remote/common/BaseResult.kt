package ru.mareanexx.common.data.remote.common

sealed class BaseResult<out D : Any, out E : Any> {
    data class Success<D : Any>(val data: D) : BaseResult<D, Nothing>()
    data class Error<E : Any>(val error: E) : BaseResult<Nothing, E>()
}