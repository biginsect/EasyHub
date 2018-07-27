package com.lipeng.mygithub.base.mvp

import android.support.annotation.UiThread

/**
 * @author big insect
 */
interface MvpPresenter <V: MvpView>{

    @UiThread
    fun attachView(v :V?)

    @UiThread
    fun detachView(isDetach :Boolean)
}