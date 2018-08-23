package com.biginsect.easygithub.mvp

import android.support.annotation.NonNull

/**
 * @author big insect
 */
interface MvpDelegateCallback<V: MvpView, P: MvpPresenter<V>> {

    @NonNull
    fun createPresenter():P?

    fun getPresenter():P?

    fun setPresenter(presenter:P?)

    fun getMvpView():V?
}