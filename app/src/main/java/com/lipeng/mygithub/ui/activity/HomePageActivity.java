package com.lipeng.mygithub.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.base.BaseMvpListActivity;
import com.lipeng.mygithub.constant.ToastType;
import com.lipeng.mygithub.ui.adapter.HomePageListAdapter;
import com.lipeng.mygithub.ui.contract.IHomePageContract;
import com.lipeng.mygithub.ui.presenter.HomePagePresenter;
import com.lipeng.mygithub.util.ActivitiesManager;
import com.lipeng.mygithub.util.ToastUtils;


import butterknife.BindView;

/**
 * 主页面
 * @author biginsect
 * @date 2017/12/22
 * */
public class HomePageActivity extends BaseMvpListActivity<IHomePageContract.IHomePageView,IHomePageContract.IHomePagePresenter, HomePageListAdapter>
        implements View.OnClickListener{

    /**记录按下返回键的时间*/
    private long mLastBackPressedTime = 0;
    /**两次back键间隔时间*/
    private final static long TIME_INTERVAL = 1000;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @BindView(R.id.rv_project_list) RecyclerView mProjectList;
    @BindView(R.id.home_page_toolbar) Toolbar mToolbar;
    @BindView(R.id.jump_btn) Button jump;
    @BindView(R.id.homepage_nav)  NavigationView mNavView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_homepage;
    }

    @NonNull
    @Override
    public IHomePageContract.IHomePagePresenter createPresenter() {
        presenter = new HomePagePresenter();
        return presenter;
    }

    @Override
    protected void initView(Bundle savedInstanceState){
        super.initView(savedInstanceState);
        jump.setOnClickListener(this);
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /**这里应当是点击item之后进行对应页面的跳转，这里只是把侧滑栏关闭，后期修改*/
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        setRecyclerView();
        setToolbar();
        setDrawerLayout();
    }

    private void setRecyclerView(){
        mProjectList.setLayoutManager(new LinearLayoutManager(this));
        mProjectList.setAdapter(mAdapter);
    }

    /**
     * 设置左边侧滑栏
     * */
    private void setDrawerLayout(){
        mDrawerLayout = findViewById(R.id.drawer_layout_left);
        /*侧滑栏监听器*/
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_layout_open, R.string.drawer_layout_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        /*设置弹出侧滑栏的按钮的默认logo为三横杠，不调用此方法则为箭头*/
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            /*返回按钮可用*/
            actionBar.setHomeButtonEnabled(true);
            /*显示返回图标*/
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected HomePageListAdapter getAdapter() {
        return new HomePageListAdapter(this);
    }

    @Override
    protected void showDetail(Object tag) {

    }

    @Override
    protected boolean onLongClickShow(Object tag) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump_btn:
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /*两次按下back键的时间小于1s，则finish*/
        if (System.currentTimeMillis() - mLastBackPressedTime < TIME_INTERVAL){
            ToastUtils.cancelToast();
            ActivitiesManager.INSTANCE.appExit(this);
        }else {
            mLastBackPressedTime = System.currentTimeMillis();
            ToastUtils.showLongToast(this, "Press again to exit.", ToastType.INFO);
        }
    }

    /**
     * 跳转到此页面（homepage）
     * @param context 上一页面的上下文
     * */
    public static void show(Context context){
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }
}
