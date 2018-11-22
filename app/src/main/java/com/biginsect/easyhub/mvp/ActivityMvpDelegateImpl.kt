package com.biginsect.easyhub.mvp

import android.app.Activity
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import java.util.*

class ActivityMvpDelegateImpl<V : MvpView, P : MvpPresenter<V>>(@NonNull delegateCallback: MvpDelegateCallback<V, P>?, keepPresenterInstance: Boolean, activity: Activity?)
    : ActivityMvpDelegate<V, P> {

    companion object {
        var DEBUG = false
        internal const val KEY_MOSBY_VIEW_ID = "com.hannesdorfmann.mosby3.activity.mvp.id"
        internal const val TAG = "ActivityMvpDelegateImpl"
        fun retainPresenterInstance(keepPresenterInstance: Boolean, activity: Activity?): Boolean {
            return keepPresenterInstance && (activity?.isChangingConfigurations!! || activity.isFinishing)
        }
    }

    private var delegateCallback: MvpDelegateCallback<V, P>? = null
    private var keepPresenterInstance = false
    private var activity: Activity? = null
    private var mosbyViewId: String? = null

    init {
        when {
            null == activity -> throw NullPointerException("Activity is null")
            null == delegateCallback -> throw NullPointerException("MvpDelegateCallback is null")
            else -> {
                this.delegateCallback = delegateCallback
                this.activity = activity
                this.keepPresenterInstance = keepPresenterInstance
            }
        }
    }

    private fun createViewIdAndCreatePresenter(): P {
        val presenter = this.delegateCallback?.createPresenter()

        when (presenter) {
            null -> throw NullPointerException("Presenter returned from createPresenter() is null. Activity is " + this.activity)
            else -> {
                if (this.keepPresenterInstance) {
                    this.mosbyViewId = UUID.randomUUID().toString()
                    PresenterManager.putPresenter(this.activity, this.mosbyViewId!!, presenter)

                }
            }
        }
        return presenter
    }

    override fun onCreate(bundle: Bundle?) {
        var presenter: P? = null
        if (null != bundle && keepPresenterInstance) {
            mosbyViewId = bundle.getString(KEY_MOSBY_VIEW_ID)
            if (DEBUG) {
                Log.d(TAG, "MosbyView ID = " + this.mosbyViewId + " for MvpView: " + this.delegateCallback?.getMvpView())

            }
            if (null != mosbyViewId) {
                presenter = PresenterManager.getPresenter(activity!!, mosbyViewId!!)
                if (null != presenter) {
                    if (DEBUG) {
                        Log.d(TAG, "Reused presenter " + presenter + " for view " + this.delegateCallback?.getMvpView())
                    }
                } else {
                    presenter = createViewIdAndCreatePresenter()
                    if (DEBUG) {
                        Log.d(TAG, "No presenter found although view Id was here: " + this.mosbyViewId + ". Most likely this was caused by a process death. New Presenter created" + presenter + " for view " + this.getMvpView())
                    }
                }
            }
        } else {
            presenter = createViewIdAndCreatePresenter()
            if (DEBUG) {
                Log.d(TAG, "No presenter found although view Id was here: " + this.mosbyViewId + ". Most likely this was caused by a process death. New Presenter created" + presenter + " for view " + this.getMvpView())
            }
        }

        if (null == presenter) {
            IllegalStateException("Oops, Presenter is null. This seems to be a Mosby internal bug. Please report this issue here: https://github.com/sockeqwe/mosby/issues")
        } else {
            delegateCallback?.setPresenter(presenter)
            getPresenter()?.attachView(getMvpView())
            if (DEBUG) {
                Log.d(TAG, "View " + getMvpView() + " attached to Presenter " + presenter);
            }
        }
    }

    private fun getPresenter(): P? {
        return delegateCallback?.createPresenter()
                ?: throw NullPointerException("Presenter returned from getPresenter() is null")
    }

    private fun getMvpView(): V? {
        return delegateCallback?.getMvpView()
                ?: throw NullPointerException("View returned from getMvpView() is null")
    }

    override fun onDestroy() {
        val retainPresenterInstance = retainPresenterInstance(keepPresenterInstance, activity)
        getPresenter()?.detachView(retainPresenterInstance)
        if (!retainPresenterInstance && null != mosbyViewId) {
            PresenterManager.remove(activity, mosbyViewId)
        }

        if (DEBUG) {
            if (retainPresenterInstance) {
                Log.d("ActivityMvpDelegateImpl", "View" + this.getMvpView() + " destroyed temporarily. View detached from presenter " + this.getPresenter())
            } else {
                Log.d("ActivityMvpDelegateImpl", "View" + this.getMvpView() + " destroyed permanently. View detached permanently from presenter " + this.getPresenter())
            }
        }

    }

    override fun onPause() {
    }

    override fun onResume() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onRestart() {
    }

    override fun onContentChanged() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (keepPresenterInstance && null != outState) {
            outState.putString(KEY_MOSBY_VIEW_ID, mosbyViewId)
            if (DEBUG) {
                Log.d(TAG, "Saving MosbyViewId into Bundle. ViewId: $mosbyViewId for view" + getMvpView())
            }
        }
    }

    override fun onPostCreate(bundle: Bundle?) {
    }
}