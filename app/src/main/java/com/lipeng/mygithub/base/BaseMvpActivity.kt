package com.lipeng.mygithub.base

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import com.lipeng.mygithub.R
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
    abstract override fun createPresenter():P?

    /**
     * 数据变化时触发RecyclerView的动画
     * */
    internal fun runLayoutAnimation(recyclerView: RecyclerView){
        val layoutAnimationController = AnimationUtils.loadLayoutAnimation(recyclerView.context,
                R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = layoutAnimationController
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    override fun getPresenter(): P? {
        return presenter
    }

    override fun getMvpView(): V? {
        return this as V
    }

    fun showLoading(){

    }

    fun hideLoading(){

    }
}