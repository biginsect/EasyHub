package com.biginsect.easyhub.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.view.View
import com.biginsect.easyhub.R
import com.biginsect.easyhub.app.GlideApp
import com.biginsect.easyhub.bean.Event
import com.biginsect.easyhub.bean.Event.EventType.*
import com.biginsect.easyhub.bean.EventPayload
import com.biginsect.easyhub.bean.EventPayload.TypeMemberEventAction.*
import com.biginsect.easyhub.ui.adapter.base.BaseAdapter
import com.biginsect.easyhub.ui.adapter.base.BaseViewHolder
import com.biginsect.easyhub.ui.widget.EllipsizeLineSpan
import com.biginsect.easyhub.util.PreUtils
import com.biginsect.easyhub.util.StringUtils
import kotlinx.android.synthetic.main.list_item_homepage.view.*
import java.util.regex.Pattern

/**
 * 主页列表适配器
 * @author big insect
 * @date 2018/9/20.
 */

class EventListAdapter(context: Context) : BaseAdapter<EventListAdapter.ViewHolder, Event>(context) {

    override fun getLayoutId(): Int {
        return R.layout.list_item_homepage
    }

    override fun getViewHolder(itemView: View, viewType: Int): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val event = dataList[position]

        with(holder.itemView) {
            GlideApp.with(mContext)
                    .load(event.actor.avatarUrl)
                    .onlyRetrieveFromCache(!PreUtils.isLoadImageAvailable())
                    .into(user_avatar)
            user_name.text = event.actor.name
            activity_time.text = StringUtils.getTimeString(mContext, event.createdAt)

            holder.setAction(event)
        }
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {

        init {
            itemView.user_avatar.setOnClickListener { showProfile() }
            itemView.user_name.setOnClickListener { showProfile() }
        }

        private fun showProfile() {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val loginId = dataList[adapterPosition].actor.login
                val userAvatar = dataList[adapterPosition].actor.avatarUrl

            }
        }

        fun setAction(event: Event) {
            var actionStr: String? = null
            var descSpan: SpannableStringBuilder? = null
            val fullName = event.repo.fullName ?: null
            val typeRef = event.payload.typeRef
            val action = event.payload.action

            when (event.type) {

                CommitComment -> {
                    actionStr = String.format(getString(R.string.created_comment_on_commit))
                    descSpan = SpannableStringBuilder(event.payload.comment.body)
                }
                Create -> {
                    actionStr = when (typeRef) {
                        EventPayload.TypeRef.repository -> String.format(getString(R.string.created_repo), fullName)
                        EventPayload.TypeRef.branch -> String.format(getString(R.string.created_branch_at), event.payload.ref, fullName)
                        EventPayload.TypeRef.tag -> String.format(getString(R.string.created_tag_at), event.payload.ref, fullName)
                    }
                }
                Delete -> {
                    if (EventPayload.TypeRef.branch == typeRef) {
                        actionStr = String.format(getString(R.string.delete_branch_at), event.payload.ref, fullName)
                    } else if (EventPayload.TypeRef.tag == typeRef) {
                        actionStr = String.format(getString(R.string.delete_tag_at), event.payload.ref, fullName)
                    }
                }
                Fork -> {
                    val originalRepo = event.repo.fullName
                    val newRepo = event.actor.login + "/" + event.repo.name
                    actionStr = String.format(getString(R.string.forked_to), originalRepo, newRepo)
                }
                Gollum -> actionStr = "$action a wiki page "
                Installation -> actionStr = "$action a GitHub app"
                InstallationRepositories -> actionStr = "$action repository from an installation"
                IssueComment -> {
                    actionStr = String.format(getString(R.string.created_comment_on_issue),
                            event.payload.issue.number, event.repo.fullName)
                    descSpan = SpannableStringBuilder(event.payload.comment.body)
                }
                Issues -> {
                    val issueEventStr = getIssueEventStr(action)
                    actionStr = String.format(issueEventStr, event.payload.issue.number, event.repo.fullName)
                    descSpan = SpannableStringBuilder(event.payload.issue.title)
                }
                MarketplacePurchase -> actionStr = "$action marketplace plan "
                Member -> {
                    val memberEventStr = getMemberEventStr(action)
                    actionStr = String.format(memberEventStr, event.payload.member.login, fullName)
                }
                OrgBlock -> {
                    val orgBlockEventStr = if (EventPayload.TypeOrgBlockEventAction.blocked.name == action) {
                        getString(R.string.org_blocked_user)
                    } else {
                        getString(R.string.org_unblocked_user)
                    }
                    actionStr = String.format(orgBlockEventStr,
                            event.payload.organization.login, event.payload.blockedUser.login)
                }
                ProjectCard -> actionStr = "$action a project "
                ProjectColumn -> actionStr = "$action a project "
                Project -> actionStr = "$action a project "
                Public -> actionStr = String.format(getString(R.string.made_repo_public), fullName)
                PullRequest -> actionStr = "$action Pull request ${event.repo.fullName}"
                PullRequestReview -> {
                    val pullRequestReviewStr = getPullRequestReviewEventStr(action)
                    actionStr = String.format(pullRequestReviewStr, fullName)
                }
                PullRequestReviewComment -> {
                    val pullRequestCommentStr = getPullRequestReviewCommentEventStr(action)
                    actionStr = String.format(pullRequestCommentStr, fullName)
                    descSpan = SpannableStringBuilder(event.payload.comment.body)
                }
                Push -> {
                    val branch = event.payload.branch
                    actionStr = String.format(getString(R.string.push_to), branch, fullName)
                    descSpan = SpannableStringBuilder("")
                    val count = event.payload.commits.size
                    val maxLines = 4
                    val max = if (count > maxLines) {
                        maxLines - 1
                    } else count

                    for (i in 0 until max) {
                        val commit = event.payload.commits[i]
                        if (i != 0) {
                            descSpan.append("\n")
                        }

                        val lastLength = descSpan.length
                        val sha = commit.sha.substring(0, 7)
                        descSpan.append(sha)
                        descSpan.setSpan(TextAppearanceSpan(mContext, R.style.text_link),
                                lastLength, lastLength + sha.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                        descSpan.append(" ")
                        descSpan.append(getFirstLine(commit.message))

                        descSpan.setSpan(EllipsizeLineSpan(0), lastLength, descSpan.length, 0)
                    }

                    if (count > maxLines) {
                        descSpan.append("\n").append("...")
                    }
                }
                Release -> actionStr = String.format(getString(R.string.published_release_at),
                        event.payload.release.tagName, fullName)
                Watch -> actionStr = String.format(getString(R.string.starred_repo), fullName)
                else -> {
                    /**do nothing*/
                }
            }

            itemView.action.visibility = View.VISIBLE
            if (descSpan != null) {
                itemView.activity_desc.visibility = View.VISIBLE
                itemView.activity_desc.text = descSpan
            } else {
                itemView.activity_desc.visibility = View.GONE
            }

            actionStr = StringUtils.upCaseFirstChar(actionStr)
            actionStr = actionStr ?: ""
            val span = SpannableStringBuilder(actionStr)
            val repoFullNamePattern = Pattern.compile("([a-z]|[A-Z]|\\d|-)*/([a-z]|[A-Z]|\\d|-|\\.|_)*")
            val matcher = repoFullNamePattern.matcher(actionStr)
            while (matcher.find()) {
                span.setSpan(StyleSpan(Typeface.BOLD), matcher.start()
                        , matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            itemView.action.text = span

        }

        private fun getFirstLine(str: String?): String? {
            if (str == null || !str.contains("\n")) {
                return str
            }
            return str.substring(0, str.indexOf("\n"))
        }

        private fun getPullRequestReviewEventStr(action: String): String {
            val actionType = EventPayload.TypePullRequestReviewEventAction.valueOf(action)

            return when (actionType) {
                EventPayload.TypePullRequestReviewEventAction.submitted -> getString(R.string.submitted_pull_request_review_at)
                EventPayload.TypePullRequestReviewEventAction.edited -> getString(R.string.edited_pull_request_review_at)
                EventPayload.TypePullRequestReviewEventAction.dismissed -> getString(R.string.dismissed_pull_request_review_at)
            }
        }

        private fun getPullRequestReviewCommentEventStr(action: String): String {
            val actionType = EventPayload.TypePullRequestReviewCommentEventAction.valueOf(action)

            return when (actionType) {
                EventPayload.TypePullRequestReviewCommentEventAction.created -> getString(R.string.created_pull_request_comment_at)
                EventPayload.TypePullRequestReviewCommentEventAction.edited -> getString(R.string.edited_pull_request_comment_at)
                EventPayload.TypePullRequestReviewCommentEventAction.deleted -> getString(R.string.deleted_pull_request_comment_at)
            }
        }

        private fun getMemberEventStr(action: String): String {
            val actionType = EventPayload.TypeMemberEventAction.valueOf(action)

            return when (actionType) {
                added -> getString(R.string.added_member_to)
                deleted -> getString(R.string.deleted_member_at)
                edited -> getString(R.string.edited_member_at)
            }
        }

        private fun getIssueEventStr(action: String): String {
            val actionType = EventPayload.TypeIssueEventAction.valueOf(action)

            return when (actionType) {
                EventPayload.TypeIssueEventAction.assigned -> getString(R.string.assigned_issue_at)
                EventPayload.TypeIssueEventAction.unassigned -> getString(R.string.unassigned_issue_at)
                EventPayload.TypeIssueEventAction.labeled -> getString(R.string.labeled_issue_at)
                EventPayload.TypeIssueEventAction.unlabeled -> getString(R.string.unlabeled_issue_at)
                EventPayload.TypeIssueEventAction.opened -> getString(R.string.opened_issue_at)
                EventPayload.TypeIssueEventAction.edited -> getString(R.string.edited_issue_at)
                EventPayload.TypeIssueEventAction.milestoned -> getString(R.string.milestoned_issue_at)
                EventPayload.TypeIssueEventAction.demilestoned -> getString(R.string.demilestoned_issue_at)
                EventPayload.TypeIssueEventAction.closed -> getString(R.string.closed_issue_at)
                EventPayload.TypeIssueEventAction.reopened -> getString(R.string.reopened_issue_at)
            }
        }
    }
}