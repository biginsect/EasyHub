package com.biginsect.easyhub.http.base

import retrofit2.Response

/**
 * @author big insect
 */
class HttpResponse<T>(response: Response<T>) {

    val originalResponse: Response<T> = response

    val isSuccessful: Boolean = originalResponse.isSuccessful

    val isFromCache: Boolean = isResponseAvailable(originalResponse.raw()?.cacheResponse())

    val isFromNetwork: Boolean = isResponseAvailable(originalResponse.raw()?.networkResponse())

    fun body(): T? {
        return originalResponse.body()
    }

    private fun isResponseAvailable(response: okhttp3.Response?): Boolean {
        return null != response && 200 == response.code()
    }
}