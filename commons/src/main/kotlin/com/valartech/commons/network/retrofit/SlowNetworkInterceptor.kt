package com.valartech.commons.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Simulate a slow network by adding this interceptor to your [okhttp3.OkHttpClient.Builder].
 *
 * Note that you should add a logging interceptor before so that request logs are properly shown.
 *
 * Inspired by https://stackoverflow.com/questions/35701505/simulate-bad-network-with-retrofit-2-on-actual-i-e-non-mocked-api
 */
class SlowNetworkInterceptor(private val networkDelayMs: Long) : Interceptor {

    init {
        Timber.w("Delaying network requests")
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            Thread.sleep(networkDelayMs)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return chain.proceed(chain.request())
    }
}
