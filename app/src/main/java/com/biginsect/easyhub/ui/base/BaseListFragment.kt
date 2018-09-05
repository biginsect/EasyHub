package com.biginsect.easyhub.ui.base

import android.os.Bundle
import android.view.View

/**
 * @author big insect
 * @date 2018/9/3.
 */

abstract class BaseListFragment<V: IBaseContract.IView, P: IBaseContract.IPresenter<V>, A: BaseAdapter<*, *>>
    :BaseFragment<V, P>(), IBaseContract.IView,
        BaseViewHolder.OnItemLongClickListener, BaseViewHolder.OnItemClickListener{

    internal var adapter: A? = null
        private set

    override fun initFragment(savedInstanceState: Bundle?) {
        adapter = getAdapter()
        if (null != adapter){
            adapter!!.onItemClickListener = this
            adapter!!.onItemLongClickListener = this
        }
    }

    override fun onItemClick(position: Int, view: View?) {

    }

    override fun onItemLongClick(position: Int, view: View?): Boolean {
        return false
    }

    abstract override fun getLayoutId(): Int

    abstract fun getAdapter(): A
}