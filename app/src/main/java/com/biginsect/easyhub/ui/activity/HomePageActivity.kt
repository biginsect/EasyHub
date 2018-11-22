package com.biginsect.easyhub.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.biginsect.easyhub.R
import com.biginsect.easyhub.app.AppData
import com.biginsect.easyhub.app.GlideApp
import com.biginsect.easyhub.constant.ToastType
import com.biginsect.easyhub.ui.activity.base.BaseListActivity
import com.biginsect.easyhub.ui.adapter.EventListAdapter
import com.biginsect.easyhub.ui.contract.IHomePageContract
import com.biginsect.easyhub.ui.presenter.HomePagePresenter
import com.biginsect.easyhub.util.ActivitiesManager
import com.biginsect.easyhub.util.PreUtils
import com.biginsect.easyhub.util.StringUtils
import com.biginsect.easyhub.util.ToastUtils
import com.biginsect.easyhub.util.ViewUtils
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.drawer_left_homepage.*
import kotlinx.android.synthetic.main.layout_appbar.*

/**
 * 应当只作为容器，放置activity，starred等列表内容
 * @author big insect
 * @date 2018/8/30.
 */
class HomePageActivity : BaseListActivity<IHomePageContract.IHomePageView, IHomePageContract.IHomePagePresenter, EventListAdapter>(),
        IHomePageContract.IHomePageView {

    private var mLastBackPressedTime = 0L
    /**两次点击时间间隔在此范围内则退出app*/
    private val mTimeInterval = 1000L

    private val settingRequestCode = 100

    override fun initActivity() {
        super.initActivity()
        if (null != AppData.loggedUser) {

        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setToolbar()
        setNavMenu(R.menu.nav_homepage_menu)
        setRecyclerView()
        setDrawerLayout()

        val loginUser = AppData.loggedUser
        GlideApp.with(getActivity())
                .load(loginUser?.avatarUrl)
                .onlyRetrieveFromCache(!PreUtils.isLoadImageAvailable())
                .into(im_my_picture)
        tv_name.text = if (StringUtils.isBlankString(loginUser?.name)) {
            loginUser?.login
        } else {
            loginUser?.name
        }
        val joinTime = getString(R.string.joined_at) + " " + StringUtils.getDateStr(loginUser?.createdAt)
        tv_join_time.text = if (StringUtils.isBlankString(loginUser?.bio)) {
            joinTime
        } else {
            loginUser?.bio
        }
        tab_layout.visibility = View.GONE
    }

    private fun setRecyclerView() {
        rv_project_list.layoutManager = LinearLayoutManager(this)
        rv_project_list.adapter = mAdapter
    }

    private fun setDrawerLayout() {
        homepage_nav.setNavigationItemSelectedListener { item ->
            drawer_layout.closeDrawers()
            this@HomePageActivity.onNavigationItemSelected(item)
        }
        val drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.drawer_layout_open, R.string.drawer_layout_close)
        drawerToggle.syncState()
        drawer_layout.addDrawerListener(drawerToggle)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setToolbarTitle(getString(R.string.project_list))
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_homepage
    }

    override fun createAdapter(): EventListAdapter {
        return EventListAdapter(this)
    }

    override fun createPresenter(): IHomePageContract.IHomePagePresenter {
        presenter = HomePagePresenter()
        return presenter
    }

    override fun restartApp() {
        getActivity().finishAffinity()
        startActivity(Intent(getActivity(), SplashActivity::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (System.currentTimeMillis() - mLastBackPressedTime < mTimeInterval) {
            ToastUtils.cancel()
            ActivitiesManager.appExit(this)
        } else {
            mLastBackPressedTime = System.currentTimeMillis()
            ToastUtils.showLongToast(this, "Press again to exit.", ToastType.INFO)
        }
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        ViewUtils.selectMenuItem(homepage_nav.menu, item.itemId, true)
        drawer_layout.closeDrawer(GravityCompat.START)
        Handler().postDelayed({
            handleNavigationItemClicked(item)
        }, 250)

        return true
    }

    private fun handleNavigationItemClicked(item: MenuItem) {
        val id = item.itemId
        when (id) {
            R.id.nav_setting -> SettingActivity.show(getActivity(), settingRequestCode)
            R.id.nav_logout -> logout()
        }
    }

    private fun setNavMenu(menuId: Int) {
        if (drawer_layout != null && homepage_nav != null) {
            homepage_nav.inflateMenu(menuId)
            if (drawer_layout.indexOfChild(homepage_nav) == -1) {
                drawer_layout.addView(homepage_nav)
            }
        }
    }

    private fun logout() {
        AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(R.string.warning)
                .setMessage(R.string.warning_logout)
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog?.dismiss() }
                .setPositiveButton(R.string.okay) { dialog, _ ->
                    setResult(Activity.RESULT_CANCELED)
                    presenter.logout()
                    dialog.dismiss()
                }
                .show()
    }

    companion object {
        fun show(context: Context) {
            context.startActivity(Intent(context, HomePageActivity::class.java))
        }
    }
}