package com.lipeng.mygithub.http.base

import retrofit2.Response
import rx.Subscriber

/**
 * @author big insect
 */
open class HttpSubscriber<T> : Subscriber<Response<T>> {
    private var mObserver: HttpObserver<T>? = null

    constructor()

    constructor(response: HttpObserver<T>?){
        mObserver = response
    }

    override fun onCompleted() {

    }

    override fun onError(e: Throwable?) {
        mObserver?.onError(e)
    }

    override fun onNext(t: Response<T>?) {
        mObserver?.onSuccess(HttpResponse(t))
    }
}