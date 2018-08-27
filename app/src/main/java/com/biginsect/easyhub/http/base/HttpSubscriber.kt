package com.biginsect.easyhub.http.base

import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response

/**
 * @author big insect
 */
open class HttpSubscriber<T>(private val observer: HttpObserver<T>) : Observer<Response<T>> {

    override fun onComplete() {
        Logger.d("onComplete ", " ---- HttpSubscriber")
    }

    override fun onSubscribe(d: Disposable) {
        observer.onSubscribe(d)
    }

    override fun onNext(t: Response<T>) {
        observer.onSuccess(HttpResponse(t))
    }

    override fun onError(e: Throwable) {
        observer.onError( e)
    }
}