package com.lipeng.mygithub.base

import android.os.Bundle
import android.support.annotation.NonNull
import com.lipeng.mygithub.base.mvp.*

/**
 * @author big insect
 */
abstract class BaseMvpActivity<V:MvpView, P: MvpPresenter<V>> : BaseActivity()
    ,MvpView, MvpDelegateCallback<V, P>{
    internal var mvpDelegate: ActivityMvpDelegate<V, P>? = null
        get() {
            if (null == field){
                field = ActivityMvpDelegateImpl(this, true, this)
            }
            return field
        }
    internal var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate?.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mvpDelegate?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mvpDelegate?.onPause()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate?.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        mvpDelegate?.onRestart()
    }

    override fun onContentChanged() {
        super.onContentChanged()
        mvpDelegate?.onContentChanged()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mvpDelegate?.onPostCreate(savedInstanceState)
    }

    @NonNull
    override abstract fun createPresenter():P?

    override fun getPresenter(): P? {
        return presenter
    }

    override fun getMvpView(): V? {
        return this as V
    }
}