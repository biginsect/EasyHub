package com.biginsect.easyhub.ui.adapter.base

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.biginsect.easyhub.ui.fragment.base.BaseFragment
import com.biginsect.easyhub.util.ListUtils

/**
 * 基类，支持长按和点击事件，展示视图使用addData()方法
 * @author big insect
 */
abstract class BaseAdapter<VH : BaseViewHolder, D : Any>(context: Context)
    : RecyclerView.Adapter<VH>(),
        BaseViewHolder.OnItemClickListener,
        BaseViewHolder.OnItemLongClickListener {

    protected val mContext = context
    internal lateinit var dataList: ArrayList<D>

    protected lateinit var fragment: BaseFragment<*, *>
        private set

    internal var onItemClickListener: BaseViewHolder.OnItemClickListener? = null
    internal var onItemLongClickListener: BaseViewHolder.OnItemLongClickListener? = null

    constructor(context: Context, fragment: BaseFragment<*, *>) : this(context) {
        this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val view = LayoutInflater.from(parent?.context)
                .inflate(getLayoutId(), parent, false)
        return getViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (null != onItemClickListener) {
            holder.setOnItemClickListener(this)
        }
        if (null != onItemLongClickListener) {
            holder.setOnItemLongClickListener(this)
        }
    }

    override fun onItemClick(position: Int, view: View?) {
        onItemClickListener?.onItemClick(position, view)
    }

    override fun onItemLongClick(position: Int, view: View?): Boolean {
        if (null != onItemLongClickListener) {
            return onItemLongClickListener!!.onItemLongClick(position, view)
        }
        return false
    }

    override fun getItemCount(): Int {
        return ListUtils.getSize(dataList)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    protected fun getString(@StringRes resId: Int): String {
        return mContext.getString(resId)
    }

    /**
     * 获取到数据，通知视图更新
     * */
    fun setData(dataList: ArrayList<D>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    /**
     * 获取布局ID
     * */
    protected abstract fun getLayoutId(): Int

    /**
     * 构建ViewHolder
     * */
    protected abstract fun getViewHolder(itemView: View, viewType: Int): VH
}