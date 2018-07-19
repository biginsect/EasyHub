package com.lipeng.mygithub.base.mvp

import android.app.Activity
import android.os.Bundle
import android.view.View

/**
 * Created by lipeng-ds3 on 2018/7/19.
 */
interface FragmentMvpDelegate<V :MvpView, P :MvpPresenter<V>> {
    fun onCreate(bundle: Bundle?)

    fun onDestroy()

    fun onViewCreated(view: View?, bundle: Bundle?)

    fun onDestroyView()

    fun onPause()

    fun onResume()

    fun onStart()

    fun onStop()

    fun onActivityCreated(bundle: Bundle?)

    fun onAttach(activity: Activity?)

    fun onDetach()

    fun onSaveInstanceState(outState: Bundle?)
}