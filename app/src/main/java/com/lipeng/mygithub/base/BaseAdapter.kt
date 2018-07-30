package com.lipeng.mygithub.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lipeng.mygithub.util.ListUtils

/**
 * @author big insect
 */
abstract class BaseAdapter<VH :RecyclerView.ViewHolder, D: Any>(protected var context: Context)
    : RecyclerView.Adapter<VH>(), View.OnClickListener, View.OnLongClickListener {
    protected var dataList:ArrayList<D> = ArrayList()
    private var mClickListener :OnItemClickListener? = null
    private var mLongClickListener :OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(getLayoutId(), parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (null != mClickListener){
            holder.itemView.setOnClickListener(this)
        }
        if (null != mLongClickListener) {
            holder.itemView.setOnLongClickListener(this)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?){
        this.mClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?){
        this.mLongClickListener = listener
    }

    override fun onClick(v: View) {
        mClickListener?.onItemClick(v.tag)
    }

    override fun onLongClick(v: View?): Boolean {
        return mLongClickListener!!.onItemLongClick(v?.tag)
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
    fun addData(dataList:ArrayList<D>){
        this.dataList.clear()
        this.dataList.addAll(dataList)
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

    interface OnItemClickListener{
        fun onItemClick(tag :Any)
    }

    interface OnItemLongClickListener{
        /**
         * @return true 只执行此方法中的代码
         * @return false 继续响应其他监听中的事件
         * */
        fun onItemLongClick(tag: Any?):Boolean
    }
}