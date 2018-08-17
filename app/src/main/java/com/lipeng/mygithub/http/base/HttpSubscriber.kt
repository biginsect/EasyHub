package com.lipeng.mygithub.http.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response

/**
 * @author big insect
 */
open class HttpSubscriber<T> : Observer<Response<T>> {
    private var actual: HttpObserver<T>? = null

    constructor()

    constructor(observer: HttpObserver<T>?){
        actual = observer
    }

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        actual?.onSubscribe(d)
    }

    override fun onNext(t: Response<T>) {
        actual?.onSuccess(HttpResponse(t))
    }

    override fun onError(e: Throwable) {
        actual?.onError(error = e)
    }
}