package com.valartech.commons.network.blog

import androidx.lifecycle.MutableLiveData
import com.valartech.commons.network.google.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response
import timber.log.Timber

/**
 * https://proandroiddev.com/oversimplified-network-call-using-retrofit-livedata-kotlin-coroutines-and-dsl-512d08eadc16
 */
class CallHandler<RESPONSE : Any, DATA : Any> {
    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var client: Deferred<Response<RESPONSE>>

    /**
     * Used in conjunction with [networkCall].
     */
    fun makeCall(scope: CoroutineScope): MutableLiveData<Resource<DATA>> {
        val result = MutableLiveData<Resource<DATA>>()
        result.value = Resource.loading(null)
        scope.launch {
            var retrofitResponse: Response<*>? = null
            try {
                val httpResult = client.awaitResult()
                if (httpResult is HTTPResult.Error) {
                    retrofitResponse = httpResult.errorResponse
                }
                @Suppress("UNCHECKED_CAST")
                val response = httpResult.getOrThrow() as DataResponse<DATA>
                withContext(Main) { result.value = Resource.success(response.retrieveData()) }
            } catch (e: Exception) {
                withContext(Main) {
                    //todo send out exception
//                    val exception = AppException(e)
//                    result.value = Resource.error(exception.message, null)
                    Timber.e(e)
                    result.value = Resource.error(e.message ?: "", null, e, retrofitResponse)
                }
            }
        }
        return result
    }
}

/**
 * Cuts down the boilerplate for making network calls.
 *
 * Usage details: https://proandroiddev.com/oversimplified-network-call-using-retrofit-livedata-kotlin-coroutines-and-dsl-512d08eadc16
 */
fun <RESPONSE : DataResponse<*>, DATA : Any> networkCall(
    scope: CoroutineScope = GlobalScope,
    block: CallHandler<RESPONSE, DATA>.() -> Unit
)
    : MutableLiveData<Resource<DATA>> = CallHandler<RESPONSE, DATA>().apply(block).makeCall(scope)

interface DataResponse<T> {
    fun retrieveData(): T
}
