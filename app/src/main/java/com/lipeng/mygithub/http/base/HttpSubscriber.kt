package com.lipeng.mygithub.http.base

import com.orhanobut.logger.Logger
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
        if(null != t) {
            mObserver?.onSuccess(HttpResponse(t))
        }else{
            Logger.d("HttpSubscriber", "response is null!")
        }
    }
}