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
abstract class BaseAdapter<VH :RecyclerView.ViewHolder, D>(protected var context: Context)
    : RecyclerView.Adapter<VH>(), View.OnClickListener {
    protected var dataList:ArrayList<D> = ArrayList()
    private var mListener:OnItemClickListener? = null
    protected var mContext :Context = context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(getLayoutId(), parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (null != mListener){
            holder.itemView.setOnClickListener(this)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?){
        this.mListener = listener
    }

    override fun onClick(v: View) {
        mListener?.onItemClick(v.tag)
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
    fun addData(dataList:List<D>){
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
}