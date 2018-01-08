package com.lipeng.mygithub.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.base.BaseActivity;
import com.lipeng.mygithub.constant.ToastType;
import com.lipeng.mygithub.detailpage.OtherProjectDetailPageActivity;
import com.lipeng.mygithub.homepage.adapter.OtherProjectListRecyclerAdapter;
import com.lipeng.mygithub.homepage.model.ProjectListUsersBean;
import com.lipeng.mygithub.homepage.presenter.HomePagePresenter;
import com.lipeng.mygithub.homepage.presenter.HomePagePresenterImpl;
import com.lipeng.mygithub.homepage.view.HomePageView;
import com.lipeng.mygithub.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页面
 * @author lipeng
 * @date 2017/12/22
 * */
public class HomePageActivity extends BaseActivity implements HomePageView,View.OnClickListener{

    private HomePagePresenter mPresenter;
    /**记录按下返回键的时间*/
    private long mLastBackPressedTime = 0;
    /**两次back键间隔时间*/
    private final static long TIME_INTERVAL = 1000;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private OtherProjectListRecyclerAdapter mRecyclerAdapter;
    private List<ProjectListUsersBean> mUsersBeanList = new ArrayList<>();

    @BindView(R.id.home_page_toolbar) Toolbar mToolbar;
    @BindView(R.id.jump_btn) Button jump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
    }

    /**
     * 初始化布局文件 view等
     * */
    @Override
    protected void initView(){
        mPresenter = new HomePagePresenterImpl(this);
        ButterKnife.bind(this);
        jump.setOnClickListener(this);

        setToolbar();
        setDrawerLayout();
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
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                /*返回按钮可用*/
                actionBar.setHomeButtonEnabled(true);
                /*显示返回图标*/
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump_btn:
                mPresenter.skipToProjectDetail();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /*两次按下back键的时间小于1s，则finish*/
        if (System.currentTimeMillis() - mLastBackPressedTime < TIME_INTERVAL){
            finish();
        }else {
            mLastBackPressedTime = System.currentTimeMillis();
            ToastUtils.showLongToast(this, "Press again to exit.", ToastType.INFO);
        }
    }

    /**
     * 页面跳转
     * */
    @Override
    public void onSkipToProjectDetail() {
        OtherProjectDetailPageActivity.skip(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    /**
     * 跳转到此页面（homepage）
     * @param context 上一页面的上下文
     * */
    public static void skip(Context context){
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }
}
