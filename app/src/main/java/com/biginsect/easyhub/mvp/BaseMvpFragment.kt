package com.biginsect.easyhub.mvp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * @author big insect
 */
abstract class BaseMvpFragment<V : MvpView, P : MvpPresenter<V>>: Fragment()
        , MvpDelegateCallback<V, P>, MvpView {
    private var mvpDelegate: FragmentMvpDelegate<V, P>? = null


    protected fun getDelegate():FragmentMvpDelegate<V, P>{
        if (null == mvpDelegate){
            mvpDelegate = FragmentMvpDelegateImpl(this, this, true, true)
        }

        return mvpDelegate as FragmentMvpDelegate<V, P>
    }

//    protected var mPresenter : P? = null

    @Suppress("UNCHECKED_CAST")
    override fun getMvpView(): V? {
        return this as V
    }
    /*override fun setPresenter(presenter: P?) {
        this.mPresenter = presenter
    }

    override fun getPresenter(): P? {
        return mPresenter
    }*/

    abstract override fun createPresenter(): P?

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDelegate().onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getDelegate().onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDelegate().onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        getDelegate().onDestroy()
    }

    override fun onPause() {
        super.onPause()
        getDelegate().onPause()
    }

    override fun onResume() {
        super.onResume()
        getDelegate().onResume()
    }

    override fun onStart() {
        super.onStart()
        getDelegate().onStart()
    }

    override fun onStop() {
        super.onStop()
        getDelegate().onStop()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getDelegate().onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getDelegate().onAttach(context as Activity)
    }

    override fun onDetach() {
        super.onDetach()
        getDelegate().onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        getDelegate().onSaveInstanceState(outState)
    }
}