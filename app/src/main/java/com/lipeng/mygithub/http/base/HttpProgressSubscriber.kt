package com.lipeng.mygithub.http.base

import android.app.AlertDialog

/**
 * 网络请求开始时显示dialog，请求结束取消dialog
 * @author big insect
 */
class HttpProgressSubscriber<T>(dialog: AlertDialog?, observer: HttpObserver<T>) : HttpSubscriber<T>(observer) {
    private var mDialog:AlertDialog? = dialog

    override fun onStart() {
        super.onStart()
        if (!isUnsubscribed){
            mDialog?.show()
        }
    }

    override fun onCompleted() {
        super.onCompleted()
        mDialog?.dismiss()
    }

    override fun onError(e: Throwable?) {
        super.onError(e)
        mDialog?.dismiss()
    }
}