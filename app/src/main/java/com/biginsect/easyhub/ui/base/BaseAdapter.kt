package com.biginsect.easyhub.ui.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.biginsect.easyhub.util.ListUtils

/**
 * @author big insect
 */
abstract class BaseAdapter<VH : BaseViewHolder, D: Any>(protected var context: Context)
    : RecyclerView.Adapter<VH>(),
        BaseViewHolder.OnItemClickListener, BaseViewHolder.OnItemLongClickListener {
    protected lateinit var dataList:ArrayList<D>
    protected lateinit var fragment: BaseFragment<*, *>
    private var mClickListener : BaseViewHolder.OnItemClickListener? = null
    private var mLongClickListener : BaseViewHolder.OnItemLongClickListener? = null


    constructor(context: Context, fragment: BaseFragment<*, *>) : this(context) {
        this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val view = LayoutInflater.from(parent?.context)
                .inflate(getLayoutId(), parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (null != mClickListener){
            holder.setOnItemClickListener(this)
        }
        if (null != mLongClickListener) {
            holder.setOnItemLongClickListener(this)
        }
    }

    override fun onItemClick(position: Int, view: View?) {
        mClickListener?.onItemClick(position, view)
    }

    override fun onItemLongClick(position: Int, view: View?): Boolean {
        if(null != mLongClickListener){
            return mLongClickListener!!.onItemLongClick(position, view)
        }
        return false
    }

    fun setOnItemClickListener(listener: BaseViewHolder.OnItemClickListener?){
        this.mClickListener = listener
    }

    fun setOnItemLongClickListener(listener: BaseViewHolder.OnItemLongClickListener?){
        this.mLongClickListener = listener
    }

    override fun getItemCount(): Int {
        return ListUtils.getSize(dataList)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    /**
     * 获取到数据，通知视图更新
     * */
    fun addData(dataList: ArrayList<D>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    /**
     * 获取布局ID
     * */
    abstract fun getLayoutId():Int

    /**
     * 构建ViewHolder
     * */
    abstract fun getViewHolder(itemView:View):VH
}