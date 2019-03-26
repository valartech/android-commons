package com.valartech.commons.network.blog

import androidx.lifecycle.MutableLiveData
import com.valartech.commons.network.google.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * https://proandroiddev.com/oversimplified-network-call-using-retrofit-livedata-kotlin-coroutines-and-dsl-512d08eadc16
 */
class CallHandler<RESPONSE : Any, DATA : Any> {
    lateinit var client: Deferred<Response<RESPONSE>>

    fun makeCall(): MutableLiveData<Resource<DATA>> {
        val result = MutableLiveData<Resource<DATA>>()
        result.value = Resource.loading(null)
        GlobalScope.launch {
            try {
                @Suppress("UNCHECKED_CAST")
                val response = client.awaitResult().getOrThrow() as DataResponse<DATA>
                withContext(Main) { result.value = Resource.success(response.retrieveData()) }
            } catch (e: Exception) {
                withContext(Main) {
                    //todo send out exception
//                    val exception = AppException(e)
//                    Timber.e(exception)
//                    result.value = Resource.error(exception.message, null)
                    result.value = Resource.error(e.message ?: "", null)
                }
            }
        }
        return result
    }
}

fun <RESPONSE : DataResponse<*>, DATA : Any> networkCall(block: CallHandler<RESPONSE, DATA>.() -> Unit)
        : MutableLiveData<Resource<DATA>> = CallHandler<RESPONSE, DATA>().apply(block).makeCall()

interface DataResponse<T> {
    fun retrieveData(): T
}