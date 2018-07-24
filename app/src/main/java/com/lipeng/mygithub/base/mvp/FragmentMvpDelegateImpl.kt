package com.lipeng.mygithub.base.mvp

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import java.util.*

/**
 * @author big insect
 */
class FragmentMvpDelegateImpl<V :MvpView, P :MvpPresenter<V>>(fragment: Fragment?, delegateCallback: MvpDelegateCallback<V, P>?, keepPresenterOnBackStack: Boolean, keepPresenterInstanceDuringScreenOrientationChanges :Boolean)
    :FragmentMvpDelegate<V, P>{
    companion object {
        var DEBUG = false
        private const val DEBUG_TAG = "FragmentMvpDelegateImpl"
        internal const val KEY_MOSBY_VIEW_ID = "com.hannesdorfmann.mosby3.fragment.mvp.id"

    }
    internal var mosbyViewId: String? = null
    internal var fragment: Fragment? = null
    internal var keepPresenterOnBackStack :Boolean = false
    internal  var  keepPresenterInstanceDuringScreenOrientationChanges: Boolean = false
    private var onViewCreatedCalled = false
    private var delegateCallback :MvpDelegateCallback<V, P>? = null

    init {
        when{
            delegateCallback == null -> throw NullPointerException("MvpDelegateCallback is null!")
            fragment == null -> throw NullPointerException("Fragment is null!")
            !keepPresenterInstanceDuringScreenOrientationChanges && keepPresenterOnBackStack -> IllegalArgumentException("It is not possible to keep the presenter on backstack, but NOT keep presenter through screen orientation changes. Keep presenter on backstack also requires keep presenter through screen orientation changes to be enabled")

            else -> {
                this.fragment = fragment
                this.delegateCallback = delegateCallback
                this.keepPresenterInstanceDuringScreenOrientationChanges = keepPresenterInstanceDuringScreenOrientationChanges
                this.keepPresenterOnBackStack = keepPresenterOnBackStack
            }
        }
    }

    private fun createViewIdAndCreatePresenter():P{
        val presenter :P?= this.delegateCallback?.createPresenter()
        if(null == presenter){
            throw NullPointerException("Presenter returned from createPresenter() is null. Activity is "+ this.getActivity())
        }else{
            if(keepPresenterInstanceDuringScreenOrientationChanges){
                this.mosbyViewId = UUID.randomUUID().toString()
                PresenterManager.putPresenter(getActivity(), mosbyViewId!!, presenter)
            }
        }
        return presenter
    }

    override fun onCreate(bundle: Bundle?) {
    }

    override fun onDestroy() {
    }

    override fun onViewCreated(view: View?, bundle: Bundle?) {
        val presenter: P?
        if(null != bundle && keepPresenterInstanceDuringScreenOrientationChanges){
            mosbyViewId = bundle.getString(KEY_MOSBY_VIEW_ID)
            if(DEBUG){
                Log.d(DEBUG_TAG, "MosbyView ID = $mosbyViewId for MvpView:  ${delegateCallback?.getMvpView()}")
            }
            if (null != mosbyViewId && null != PresenterManager.getPresenter(getActivity(), mosbyViewId!!)){
                presenter = PresenterManager.getPresenter(getActivity(), mosbyViewId!!)
                if (DEBUG){
                    Log.d(DEBUG_TAG, "Reused presenter $presenter for View ${delegateCallback?.getMvpView()}")
                }
            }else{
                presenter = createViewIdAndCreatePresenter()
                if (DEBUG){
                    Log.d(DEBUG_TAG, "No presenter found although view Id was here: $mosbyViewId \". Most likely this was caused by a process death. New Presenter created $presenter for View ${getMvpView()}")
                }
            }
        }else{
            presenter = createViewIdAndCreatePresenter()
            if (DEBUG){
                Log.d(DEBUG_TAG, "New presenter $presenter for View ${getMvpView()}" )
            }
        }

        when(presenter){
            null -> throw IllegalStateException("Oops, Presenter is null. This seems to be a Mosby internal bug. Please report this issue here: https://github.com/sockeqwe/mosby/issues")

            else -> {
                delegateCallback?.setPresenter(presenter)
                getPresenter().attachView(getMvpView())
                if(DEBUG){
                    Log.d(DEBUG_TAG, "view ${getMvpView()}")
                }
                this.onViewCreatedCalled = true
            }
        }
    }

    override fun onDestroyView() {
        onViewCreatedCalled  = false
        val activity = getActivity()
        val retainPresenterInstance = retainPresenterInstance()
        val presenter = getPresenter()
        presenter.detachView(retainPresenterInstance)
        if(!retainPresenterInstance && null != mosbyViewId){
            PresenterManager.remove(activity, mosbyViewId)
        }

        if (DEBUG){
            Log.d(DEBUG_TAG, "detached MvpView from Presenter. MvpView ${delegateCallback?.getMvpView() } Presenter $presenter")
            Log.d(DEBUG_TAG, "Retaining presenter instance: $retainPresenterInstance  $presenter")
        }
    }

    override fun onPause() {
    }

    override fun onResume() {
    }

    override fun onStart() {
        if (onViewCreatedCalled){
            throw IllegalStateException("It seems that you are using ${delegateCallback?.javaClass?.canonicalName}  as headless (UI less) fragment (because onViewCreated() has not been called or maybe delegation misses that part). Having a Presenter without a View (UI) doesn't make sense. Simply use an usual fragment instead of an MvpFragment if you want to use a UI less Fragment")
        }
    }

    override fun onStop() {
    }

    override fun onActivityCreated(bundle: Bundle?) {
    }

    override fun onAttach(activity: Activity?) {
    }

    override fun onDetach() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (null != outState &&(keepPresenterInstanceDuringScreenOrientationChanges ||keepPresenterOnBackStack)){
            outState.putString(KEY_MOSBY_VIEW_ID, mosbyViewId)
            if (DEBUG){
                Log.d(DEBUG_TAG, "Saving MosbyViewId into Bundle. ViewId: $mosbyViewId")
            }
        }
    }

    internal fun retainPresenterInstance(): Boolean{
        return if (getActivity().isChangingConfigurations){
            keepPresenterInstanceDuringScreenOrientationChanges
        }else{
            when(getActivity().isFinishing){
                true -> false
                else -> {
                    fragment?.isRemoving ?: throw NullPointerException("fragment is Null")
                }
            }
        }
    }

    private fun getActivity():Activity{
        /**a ?: b ------- a != null 返回a  否则返回b*/
        return fragment?.activity ?: throw NullPointerException("Activity returned by Fragment.getActivity() is null. Fragment is $fragment")
    }

    private fun getMvpView(): V{
        return delegateCallback?.getMvpView() ?: throw NullPointerException("View returned from getMvpView() is null")
    }

    private fun getPresenter() :P{
        return delegateCallback?.getPresenter() ?: throw NullPointerException("Presenter returned from getPresenter() is null")
    }
}