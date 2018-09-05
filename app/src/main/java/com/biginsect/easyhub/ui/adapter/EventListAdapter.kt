package com.biginsect.easyhub.ui.adapter

import android.content.Context
import android.view.View
import com.biginsect.easyhub.R
import com.biginsect.easyhub.app.GlideApp
import com.biginsect.easyhub.bean.UserEvents
import com.biginsect.easyhub.ui.adapter.base.BaseAdapter
import com.biginsect.easyhub.ui.adapter.base.BaseViewHolder
import com.biginsect.easyhub.util.StringUtils
import kotlinx.android.synthetic.main.list_item_homepage.view.*

/**
 * 主页user列表适配器
 * @author big insect
 * @date 2018/8/31.
 */
class EventListAdapter(context: Context): BaseAdapter<EventListAdapter.ViewHolder, UserEvents>(context) {

    override fun getLayoutId(): Int {
        return R.layout.list_item_homepage
    }

    override fun getViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val event = dataList[position]

        with(holder.itemView){
            GlideApp.with(mContext).load(event.actor.avatarUrl).into(holder.itemView.avatar)
            user_name.text = event.actor.login
            update_at.text = StringUtils.getTimeString(mContext, event.createdAt)
        }
    }

    class ViewHolder(itemView: View): BaseViewHolder(itemView)
}