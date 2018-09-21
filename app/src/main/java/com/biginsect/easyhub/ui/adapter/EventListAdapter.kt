package com.biginsect.easyhub.ui.adapter

import android.content.Context
import android.view.View
import com.biginsect.easyhub.R
import com.biginsect.easyhub.app.GlideApp
import com.biginsect.easyhub.bean.Event
import com.biginsect.easyhub.bean.Event.EventType.*
import com.biginsect.easyhub.ui.adapter.base.BaseAdapter
import com.biginsect.easyhub.ui.adapter.base.BaseViewHolder
import com.biginsect.easyhub.util.StringUtils
import kotlinx.android.synthetic.main.list_item_homepage.view.*

/**
 * 主页列表适配器
 * @author big insect
 * @date 2018/9/20.
 */

class EventListAdapter(context: Context):BaseAdapter<EventListAdapter.ViewHolder, Event>(context) {

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
            GlideApp.with(mContext).load(event.actor.avatarUrl).into(user_avatar)
            user_name.text = event.actor.name
            activity_time.text = StringUtils.getTimeString(mContext, event.createdAt)

            holder.setAction(event)
        }
    }

    inner class ViewHolder(itemView: View):BaseViewHolder(itemView){
        fun setAction(event: Event){
            var actionStr:String

            when(event.type){

                CommitComment -> actionStr = String.format(getString(R.string.created_comment_on_commit))
                Create -> TODO()
                Delete -> TODO()
                Fork -> TODO()
                Gollum -> TODO()
                Installation -> TODO()
                InstallationRepositories -> TODO()
                IssueComment -> TODO()
                Issues -> TODO()
                MarketplacePurchase -> TODO()
                Member -> TODO()
                OrgBlock -> TODO()
                ProjectCard -> TODO()
                ProjectColumn -> TODO()
                Project -> TODO()
                Public -> TODO()
                PullRequest -> TODO()
                PullRequestReview -> TODO()
                PullRequestReviewComment -> TODO()
                Push -> TODO()
                Release -> TODO()
                Watch -> TODO()
                Deployment -> TODO()
                DeploymentStatus -> TODO()
                Membership -> TODO()
                Milestone -> TODO()
                Organization -> TODO()
                PageBuild -> TODO()
                Repository -> TODO()
                Status -> TODO()
                Team -> TODO()
                TeamAdd -> TODO()
                Label -> TODO()
                Download -> TODO()
                Follow -> TODO()
                ForkApply -> TODO()
                Gist -> TODO()
                else ->{

                }
            }
        }
    }
}