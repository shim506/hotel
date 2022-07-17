package com.example.hotel.network

sealed class NetworkResult<T : Any> {
    class Success<T : Any>(val resultData: T) : NetworkResult<T>()
    class Error<T : Any>(val code: Int, val message: String) : NetworkResult<T>()
    class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()
    companion object {
        const val NULL_BODY_ERROR = 0
        const val LAST_PAGE = 400
        const val LAST_PAGE_MESSAGE = "마지막페이지 입니다."
    }
}

suspend fun <T : Any> NetworkResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Success<T>) {
        executable(resultData)
    }
}

suspend fun <T : Any> NetworkResult<T>.onError(
    executable: suspend (code: Int, message: String?) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Error<T>) {
        executable(code, message)
    }
}

suspend fun <T : Any> NetworkResult<T>.onException(
    executable: suspend (e: Throwable) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Exception<T>) {
        executable(e)
    }
}
