package com.valartech.commons.network.blog

import okhttp3.Response
import retrofit2.HttpException


/**
 * Sealed class of HTTP result
 */
sealed class HTTPResult<out T : Any> {
    /**
     * Successful result of request without errors
     */
    class Ok<out T : Any>(
        val value: T,
        override val response: Response
    ) : HTTPResult<T>(),
        ResponseResult {
        override fun toString() = "HTTPResult.Ok{value=$value, response=$response}"
    }

    /**
     * HTTP error
     */
    class Error(
        override val exception: HttpException,
        override val response: Response
    ) : HTTPResult<Nothing>(),
        ErrorResult,
        ResponseResult {
        override fun toString() = "HTTPResult.Error{exception=$exception}"
    }

    /**
     * Network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response
     */
    class Exception(
        override val exception: Throwable
    ) : HTTPResult<Nothing>(),
        ErrorResult {
        override fun toString() = "HTTPResult.Exception{$exception}"
    }

}

/**
 * Interface for [HTTPResult] classes with [okhttp3.Response]: [HTTPResult.Ok] and [HTTPResult.Error]
 */
interface ResponseResult {
    val response: Response
}

/**
 * Interface for [HTTPResult] classes that contains [Throwable]: [HTTPResult.Error] and [HTTPResult.Exception]
 */
interface ErrorResult {
    val exception: Throwable
}

/**
 * Returns [HTTPResult.Ok.value] or `null`
 */
fun <T : Any> HTTPResult<T>.getOrNull(): T? =
    (this as? HTTPResult.Ok)?.value

/**
 * Returns [HTTPResult.Ok.value] or [default]
 */
fun <T : Any> HTTPResult<T>.getOrDefault(default: T): T =
    getOrNull() ?: default

/**
 * Returns [HTTPResult.Ok.value] or throw [throwable] or [ErrorResult.exception]
 */
fun <T : Any> HTTPResult<T>.getOrThrow(throwable: Throwable? = null): T {
    return when (this) {
        is HTTPResult.Ok -> value
        is HTTPResult.Error -> throw throwable ?: exception
        is HTTPResult.Exception -> throw throwable ?: exception
    }
}
