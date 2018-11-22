package com.biginsect.easyhub.ui.activity.base

import android.os.Bundle
import android.view.View
import com.biginsect.easyhub.ui.adapter.base.BaseAdapter
import com.biginsect.easyhub.ui.adapter.base.BaseViewHolder
import com.biginsect.easyhub.ui.contract.base.IBaseContract

/**
 * @author big insect
 * @date 2018/9/3.
 */

abstract class BaseListActivity<V : IBaseContract.IView, P : IBaseContract.IPresenter<V>, A : BaseAdapter<*, *>>
    : BaseActivity<V, P>(), IBaseContract.IView,
        BaseViewHolder.OnItemClickListener, BaseViewHolder.OnItemLongClickListener {

    protected var mAdapter: A? = null
        private set

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mAdapter = createAdapter()
        mAdapter?.onItemClickListener = this
        mAdapter?.onItemLongClickListener = this
    }

    override fun onItemLongClick(position: Int, view: View?): Boolean {
        return false
    }

    override fun onItemClick(position: Int, view: View?) {

    }

    abstract override fun getLayoutId(): Int

    abstract fun createAdapter(): A
}