package com.biginsect.easyhub.ui.fragment.base

import android.os.Bundle
import android.view.View
import com.biginsect.easyhub.ui.adapter.base.BaseAdapter
import com.biginsect.easyhub.ui.adapter.base.BaseViewHolder
import com.biginsect.easyhub.ui.contract.base.IBaseContract

/**
 * @author big insect
 * @date 2018/9/3.
 */

abstract class BaseListFragment<V : IBaseContract.IView, P : IBaseContract.IPresenter<V>, A : BaseAdapter<*, *>>
    : BaseFragment<V, P>(), IBaseContract.IView,
        BaseViewHolder.OnItemLongClickListener, BaseViewHolder.OnItemClickListener {

    internal var adapter: A? = null
        private set

    override fun initFragment(savedInstanceState: Bundle?) {
        adapter = createAdapter()
        if (null != adapter) {
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

    abstract fun createAdapter(): A
}