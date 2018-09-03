package com.biginsect.easyhub.ui.base

import android.os.Bundle
import android.view.View

/**
 * @author big insect
 * @date 2018/9/3.
 */

abstract class BaseListActivity<V: IBaseContract.IView, P: IBaseContract.IPresenter<V>, A: BaseAdapter<*, *>>
    : BaseActivity<V, P>(),
        BaseViewHolder.OnItemClickListener, BaseViewHolder.OnItemLongClickListener{

    internal var adapter: A? = null
    private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = getAdapter()
        if (null != adapter){
            adapter!!.onItemLongClickListener = this
            adapter!!.onItemClickListener = this
        }
    }

    override fun onItemLongClick(position: Int, view: View?): Boolean {
        return false
    }

    override fun onItemClick(position: Int, view: View?) {

    }

    abstract override fun getLayoutId(): Int

    abstract fun getAdapter(): A?
}