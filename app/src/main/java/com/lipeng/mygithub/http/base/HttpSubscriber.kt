package com.lipeng.mygithub.http.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response

/**
 * @author big insect
 */
open class HttpSubscriber<T> : Observer<Response<T>> {
    private var mObserver: HttpObserver<T>? = null

    constructor()

    constructor(observer: HttpObserver<T>?){
        mObserver = observer
    }

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        mObserver?.onSubscribe(d)
    }

    override fun onNext(t: Response<T>) {
        mObserver?.onSuccess(HttpResponse(t))
    }

    override fun onError(e: Throwable) {
        mObserver?.onError(error = e)
    }
}