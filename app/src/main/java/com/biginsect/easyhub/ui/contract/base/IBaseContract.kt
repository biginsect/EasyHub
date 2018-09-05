package com.biginsect.easyhub.ui.contract.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import com.biginsect.easyhub.mvp.MvpPresenter
import com.biginsect.easyhub.mvp.MvpView

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

        fun showProgressDialog(msg: String)

        fun dismissProgressDialog()

        fun getProgressDialog(msg: String): ProgressDialog
    }

    interface IPresenter<V: IView> : MvpPresenter<V> {

        fun onSaveInstanceState(outState: Bundle?)

        fun onRestoreInstanceState(outState: Bundle?)

        fun getContext():Context
    }
}