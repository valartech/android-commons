package com.valartech.commons.network.google

import com.valartech.commons.network.google.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null,
    val retrofitResponse: retrofit2.Response<*>? = null
) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data)
        }

        fun <T> error(
            msg: String,
            data: T? = null,
            throwable: Throwable? = null,
            retrofitResponse: retrofit2.Response<*>? = null
        ): Resource<T> {
            return Resource(ERROR, data, msg, throwable, retrofitResponse)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data)
        }
    }
}

/**
 * Status of a resource that is provided to the UI.
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
