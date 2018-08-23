package com.biginsect.easygithub.mvp

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
        get() {
            if (null == field){
                field = FragmentMvpDelegateImpl(this, this, true, true)
            }
            return field
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
        mvpDelegate?.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mvpDelegate?.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate?.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate?.onDestroy()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpDelegate?.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mvpDelegate?.onAttach(context as Activity)
    }

    override fun onDetach() {
        super.onDetach()
        mvpDelegate?.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mvpDelegate?.onSaveInstanceState(outState)
    }
}