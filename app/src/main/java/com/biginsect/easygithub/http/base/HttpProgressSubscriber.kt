package com.biginsect.easygithub.http.base

import android.app.AlertDialog

/**
 * 网络请求开始时显示dialog，请求结束取消dialog
 * @author big insect
 */
class HttpProgressSubscriber<T>(dialog: AlertDialog?, observer: HttpObserver<T>) : HttpSubscriber<T>(observer) {
    private var mDialog:AlertDialog? = dialog


}