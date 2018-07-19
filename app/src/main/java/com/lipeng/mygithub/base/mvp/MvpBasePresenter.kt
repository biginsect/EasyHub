package com.lipeng.mygithub.base.mvp

import android.support.annotation.UiThread
import java.lang.ref.WeakReference


/**
 * @author big insect
 */
class MvpBasePresenter<in V: MvpView> :MvpPresenter<V>{
    private var viewRef:WeakReference<V?>? = null
    var view:MvpView? = null
        private set
        @UiThread
        get() {return viewRef?.get()}

    @UiThread
    fun isViewAttached():Boolean{
        return viewRef?.get() != null
    }

    @UiThread
    override fun attachView(v: V?) {
        viewRef = WeakReference(v)
    }

    @UiThread
    override fun detachView(isDetach: Boolean) {
        viewRef?.clear()
        viewRef = null
    }
}