package com.biginsect.easyhub.ui.fragment

import android.view.View
import com.biginsect.easyhub.R
import com.biginsect.easyhub.ui.adapter.EventListAdapter
import com.biginsect.easyhub.ui.contract.IActivityContract
import com.biginsect.easyhub.ui.fragment.base.BaseListFragment
import com.biginsect.easyhub.ui.presenter.ActivityPresenter
import com.biginsect.easyhub.util.BundleHelper

/**
 * 单个项目中的Activity，即forked ，starred 等动态内容，是一个列表
 * @author big insect
 * @date 2018/8/31.
 */
class ActivityFragment: BaseListFragment<IActivityContract.IActivityView, IActivityContract.IActivityPresenter, EventListAdapter>(),
        IActivityContract.IActivityView{

    enum class ActivityType{
        NEWS, GLOBAL_NEWS
    }

    fun create(type: ActivityType, user: String): ActivityFragment{
        val fragment = ActivityFragment()
        fragment.arguments = BundleHelper.builder().put("type", type)
                .put("user", user).build()

        return fragment
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project_list
    }

    override fun createPresenter(): IActivityContract.IActivityPresenter {
        presenter = ActivityPresenter()
        return presenter
    }

    override fun createAdapter(): EventListAdapter {
        return EventListAdapter(context)
    }

    override fun onItemClick(position: Int, view: View?) {
        super.onItemClick(position, view)
        val event = adapter?.dataList?.get(position) ?: return

        val owner = event.repo.fullName.split("/")[0]
        val repoName = event.repo.fullName.split("/")[1]
    }
}