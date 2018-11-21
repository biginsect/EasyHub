package com.biginsect.easyhub.ui.activity.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.annotation.CallSuper
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.biginsect.easyhub.app.AppData
import com.biginsect.easyhub.constant.ToastType
import com.biginsect.easyhub.mvp.BaseMvpActivity
import com.biginsect.easyhub.ui.activity.LoginActivity
import com.biginsect.easyhub.ui.activity.SplashActivity
import com.biginsect.easyhub.ui.contract.base.IBaseContract
import com.biginsect.easyhub.util.ActivitiesManager
import com.biginsect.easyhub.util.ToastUtils
import com.orhanobut.logger.Logger
import com.thirtydegreesray.dataautoaccess.DataAutoAccess
import java.lang.ref.SoftReference

/**
 * @author big insect
 * @date 2018/9/5.
 */

abstract class BaseActivity<V: IBaseContract.IView, P: IBaseContract.IPresenter<V>>
    :BaseMvpActivity<V, P>(), IBaseContract.IView {

    private val tag = "BaseActivity"
    private var mSoftActivity: SoftReference<Activity>? = null
    private var currentActivity: BaseActivity<V, P>? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.d(tag, "-----onCreate()")
        if ((null == AppData.authUser ||null == AppData.loggedUser)
                && this.javaClass != SplashActivity::class.java
                && this.javaClass != LoginActivity::class.java){
            super.onCreate(savedInstanceState)
            finishAffinity()
            startActivity(Intent(getActivity(), SplashActivity::class.java))
            return
        }

        super.onCreate(savedInstanceState)
        DataAutoAccess.getData(this, savedInstanceState)
        presenter.onRestoreInstanceState(savedInstanceState ?: intent.extras)

        if(null != savedInstanceState && null == AppData.authUser){
            DataAutoAccess.getData(AppData, savedInstanceState)
        }

        if (0 != getLayoutId()){
            setContentView(getLayoutId())
        }

        initActivity()
        initView(savedInstanceState)
        mSoftActivity = SoftReference(this)
        ActivitiesManager.addActivity(mSoftActivity!!)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        DataAutoAccess.saveData(this, outState)
        presenter?.onSaveInstanceState(outState)
        if (this == currentActivity){
            DataAutoAccess.saveData(AppData, outState)
        }
    }

    override fun onResume() {
        Logger.d(tag, "----onResume()")
        super.onResume()
        currentActivity = getActivity()
    }

    override fun onDestroy() {
        Logger.d(tag, "----onDestroy()")
        super.onDestroy()
        if(this == currentActivity){
            currentActivity = null
        }

        ActivitiesManager.finishActivity(mSoftActivity)
        mSoftActivity = null
    }

    override fun finish() {
        ActivitiesManager.finishActivity(mSoftActivity)
        mSoftActivity = null
        super.finish()
    }

    internal fun setToolbarBackAvailable(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    internal fun setToolbarTitle(title: String){
        supportActionBar?.title = title
    }

    internal fun finishDelay(mill: Int){
        Handler().postDelayed({ finish() }, mill.toLong())
    }

    internal fun finishDelay(){
        finishDelay(1000)
    }

    internal fun hideSoftKeyboard(view: View){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun showLoginPage() {
        finishDelay()
        startActivity(Intent(getActivity(), LoginActivity::class.java))
    }

    override fun showError(msg: String) {
        ToastUtils.showShortToast(getActivity(), msg, ToastType.ERROR)
    }

    override fun showInfo(msg: String) {
        ToastUtils.showShortToast(getActivity(), msg, ToastType.INFO)
    }

    override fun showWarning(msg: String) {
        ToastUtils.showShortToast(getActivity(), msg, ToastType.WARNING)
    }

    override fun showSuccess(msg: String) {
        ToastUtils.showShortToast(getActivity(), msg, ToastType.SUCCESS)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun getProgressDialog(msg: String): ProgressDialog {
        if (null == mProgressDialog){
            mProgressDialog = ProgressDialog(getActivity())
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


    protected fun getActivity(): BaseActivity<V, P> {
        return this
    }

    protected abstract fun getLayoutId(): Int

    @CallSuper
    protected open fun initView(savedInstanceState: Bundle?){

    }

    @CallSuper
    protected open fun initActivity(){

    }
}