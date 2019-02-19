package com.valartech.commons.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * https://stackoverflow.com/questions/43051558/dagger-retrofit-adding-auth-headers-at-runtime/43083639#43083639
 */
@Singleton
class AuthHeaderInterceptor @Inject constructor() : Interceptor {

    companion object {
        private const val AUTH_HEADER_KEY = "Authorization"
    }

    var sessionToken: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()
        sessionToken?.let {
            requestBuilder.addHeader(AUTH_HEADER_KEY, "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
