package com.valartech.commons.network.google

import com.valartech.commons.network.google.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
class Resource<out T>(status: Status, val data: T?, val message: String?) {

    val status: Status? = status
    get() = if (field?.isHandled == false) {
        field.isHandled = true
        field
    } else {
        null
    }


    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }

//    fun getStatusIfNotHandled(): Status? {
//        return status.isHandled.let { null } ?: status
//    }
}

/**
 * Status of a resource that is provided to the UI.
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
//enum class Status {
//    SUCCESS,
//    ERROR,
//    LOADING
//}

sealed class Status(var isHandled: Boolean = false) {
    object SUCCESS: Status()
    object ERROR: Status()
    object LOADING: Status()
}
