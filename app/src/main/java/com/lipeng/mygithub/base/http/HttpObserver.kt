package com.lipeng.mygithub.base.http

/**
 * @author big insect
 */
interface HttpObserver<T> {

    fun onError(error: Throwable?)

    fun onSuccess(response: HttpResponse<T>?)
}