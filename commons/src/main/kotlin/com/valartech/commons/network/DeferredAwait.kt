package com.valartech.commons.network

import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume


suspend fun <T : Any> Deferred<Response<T>>.awaitResult(): HTTPResult<T> {
    return suspendCancellableCoroutine { continuation ->

        GlobalScope.launch {
            try {
                val response = await()
                continuation.resume(
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let {
                            HTTPResult.Ok(it, response.raw())
                        } ?: "error".let {
                            if (response.code() == 200){
                                HTTPResult.Exception(Exception("body is empty"))
                            }
                            else{
                                HTTPResult.Exception(NullPointerException("Response body is null"))
                            }
                        }

                    } else {
                        HTTPResult.Error(HttpException(response), response.raw())
                    }
                )
            }
            catch (e:Throwable){
                //  Log.e("DeferredAwait",e.message)
                continuation.resume(HTTPResult.Exception(e))
            }
        }
        registerOnCompletion(continuation)
    }
}



private fun Deferred<Response<*>>.registerOnCompletion(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        if (continuation.isCancelled)
            try {
                cancel()
            } catch (ex: Throwable) {
                //Ignore cancel exception
                ex.printStackTrace()
            }
    }
}
