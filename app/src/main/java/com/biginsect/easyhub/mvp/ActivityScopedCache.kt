package com.biginsect.easyhub.mvp

import android.support.annotation.NonNull
import android.support.v4.util.ArrayMap


/**
 * @author big insect
 */
class ActivityScopedCache {
    private val presenterMap = ArrayMap<String, PresenterHolder>()

    fun clear(){
        presenterMap.clear()
    }

    @Suppress("UNCHECKED_CAST")
    fun <P> getPresenter(@NonNull viewId :String):P {
        val holder : PresenterHolder? = presenterMap[viewId]
        return holder?.presenter as P
    }

    @Suppress("UNCHECKED_CAST")
    fun <VS> getViewState(@NonNull viewId: String) : VS{
        val holder : PresenterHolder? = presenterMap[viewId]
        return holder?.viewState as VS
    }

    fun putPresenter(@NonNull viewId: String?, @NonNull presenter: MvpPresenter<*>?){
        when {
            viewId == null -> throw NullPointerException("viewId is null")
            presenter == null -> throw NullPointerException("Presenter is null")

            else -> {
                var holder = presenterMap[viewId]
                if (holder == null){
                    holder = PresenterHolder()
                    holder.presenter = presenter
                    presenterMap[viewId] = holder
                }else{
                    holder.presenter = presenter
                }
            }
        }
    }

    fun putViewState(@NonNull viewId: String?, @NonNull viewState: Any?){
        when{
            viewId == null -> throw NullPointerException("ViewId is null")
            viewState == null -> throw NullPointerException("ViewState is null")
            else -> {
                var holder = presenterMap[viewId]
                if (holder == null){
                    holder = PresenterHolder()
                    holder.viewState = viewState
                    presenterMap[viewId] = holder
                }else{
                    holder.viewState = viewState
                }
            }
        }
    }

    fun remove(@NonNull viewId: String?){
        if (viewId == null) throw NullPointerException("ViewId is null")
        else presenterMap.remove(viewId)
    }

    internal class PresenterHolder {
        var presenter: MvpPresenter<*>? = null
        internal var viewState: Any? = null
    }
}