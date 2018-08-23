package com.biginsect.easygithub.ui.base

import android.content.Context
import android.os.Bundle
import com.biginsect.easygithub.mvp.MvpPresenter
import com.biginsect.easygithub.mvp.MvpView

/**
 * @author big insect
 * @date 2018/8/23.
 */
interface IBaseContract {

    interface IView: MvpView {

        fun showInfo(msg: String)

        fun showSuccess(msg: String)

        fun showError(msg: String)

        fun showWarning(msg: String)

        fun showLoading()

        fun hideLoading()

        fun showLoginPage()
    }

    interface IPresenter<V: IView> : MvpPresenter<V> {

        fun onSaveInstanceState(outState: Bundle?)

        fun onRestoreInstanceState(outState: Bundle?)

        fun getContext():Context
    }
}