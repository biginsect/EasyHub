package com.biginsect.easyhub.ui.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.biginsect.easyhub.constant.ToastType
import com.biginsect.easyhub.mvp.BaseMvpFragment
import com.biginsect.easyhub.ui.activity.LoginActivity
import com.biginsect.easyhub.util.ToastUtils
import com.thirtydegreesray.dataautoaccess.DataAutoAccess

/**
 * @author big insect
 * @date 2018/9/5.
 */

abstract class BaseFragment<V: IBaseContract.IView, P: IBaseContract.IPresenter<V>>
    : BaseMvpFragment<V, P>(), IBaseContract.IView {

    protected lateinit var rootView: View
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataAutoAccess.getData(this, savedInstanceState)
        DataAutoAccess.getData(this, arguments)
        if(null != presenter){
            presenter.onRestoreInstanceState(savedInstanceState)
            presenter.onRestoreInstanceState(arguments)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        DataAutoAccess.saveData(this, outState)

        presenter?.onSaveInstanceState(outState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)
        initView(rootView)
        initFragment(savedInstanceState)

        return rootView
    }

    override fun showLoginPage() {
        activity.finishAffinity()
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun showError(msg: String) {
        ToastUtils.showShortToast(activity, msg, ToastType.ERROR)
    }

    override fun showInfo(msg: String) {
        ToastUtils.showShortToast(activity, msg, ToastType.INFO)
    }

    override fun showWarning(msg: String) {
        ToastUtils.showShortToast(activity, msg, ToastType.WARNING)
    }

    override fun showSuccess(msg: String) {
        ToastUtils.showShortToast(activity, msg, ToastType.SUCCESS)
    }

    override fun getProgressDialog(msg: String): ProgressDialog {
        if (null == mProgressDialog){
            mProgressDialog = ProgressDialog(activity)
            mProgressDialog!!.setCancelable(false)
        }

        mProgressDialog!!.setMessage(msg)

        return mProgressDialog!!
    }

    override fun showProgressDialog(msg: String) {
        getProgressDialog(msg)
        mProgressDialog?.show()
    }

    override fun dismissProgressDialog() {
        mProgressDialog?.dismiss()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    abstract override fun createPresenter(): P

    internal abstract fun initFragment(savedInstanceState: Bundle?)

    internal abstract fun initView(view: View)

    internal abstract fun getLayoutId():Int
}