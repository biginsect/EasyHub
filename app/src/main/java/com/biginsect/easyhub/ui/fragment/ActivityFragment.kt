package com.biginsect.easyhub.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.biginsect.easyhub.R
import com.biginsect.easyhub.ui.adapter.EventListAdapter
import com.biginsect.easyhub.ui.contract.IActivityContract
import com.biginsect.easyhub.ui.fragment.base.BaseListFragment
import com.biginsect.easyhub.ui.presenter.ActivityPresenter
import com.biginsect.easyhub.util.BundleHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_project_list.*

/**
 * 单个项目中的Activity，即forked ，starred 等动态内容，是一个列表
 * @author big insect
 * @date 2018/8/31.
 */
class ActivityFragment: BaseListFragment<IActivityContract.IActivityView, IActivityContract.IActivityPresenter, EventListAdapter>(),
        IActivityContract.IActivityView{
    /**标志位，避免重复设置滑动状态增加开销*/
    private var mIsScrolling = false

    enum class ActivityType{
        NEWS, GLOBAL_NEWS
    }

    fun create(type: ActivityType, user: String): ActivityFragment{
        val fragment = ActivityFragment()
        fragment.arguments = BundleHelper.builder().put("type", type)
                .put("user", user).build()

        return fragment
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        super.initFragment(savedInstanceState)
        rv_project_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING){
                    mIsScrolling = true
                    Glide.with(this@ActivityFragment).pauseRequests()
                }else if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (mIsScrolling){
                        Glide.with(this@ActivityFragment).resumeRequests()
                    }

                    mIsScrolling = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
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