package com.biginsect.easygithub.mvp

import android.os.Bundle

/**
 * @big insect
 */
interface ActivityMvpDelegate<V : MvpView, P: MvpPresenter<V>> {
    fun onCreate(bundle: Bundle?)

    fun onDestroy()

    fun onPause()

    fun onResume()

    fun onStart()

    fun onStop()

    fun onRestart()

    fun onContentChanged()

    fun onSaveInstanceState(outState: Bundle?)

    fun onPostCreate(bundle: Bundle?)
}