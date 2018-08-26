package com.biginsect.easyhub.http.base

import android.app.AlertDialog
import io.reactivex.disposables.Disposable

/**
 * 网络请求开始时显示dialog，请求结束取消dialog
 * @author big insect
 */
class HttpProgressSubscriber<T>(dialog: AlertDialog?, observer: HttpObserver<T>) : HttpSubscriber<T>(observer) {
    private var mDialog = dialog

    override fun onSubscribe(d: Disposable) {
        super.onSubscribe(d)
        mDialog?.show()
    }

    override fun onComplete() {
        super.onComplete()
        mDialog?.dismiss()
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        mDialog?.dismiss()
    }
}