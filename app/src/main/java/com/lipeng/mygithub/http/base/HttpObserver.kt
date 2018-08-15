package com.lipeng.mygithub.http.base

import io.reactivex.disposables.Disposable

/**
 * @author big insect
 */
interface HttpObserver<T> {

    fun onError(error: Throwable)

    fun onSuccess(response: HttpResponse<T>)

    fun onSubscribe(d:Disposable)
}