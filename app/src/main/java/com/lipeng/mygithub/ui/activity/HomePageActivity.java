package com.lipeng.mygithub.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lipeng.mygithub.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * 主页面
 * @author lipeng
 * @date 2017/12/22
 * */
public class HomePageActivity extends BaseActivity implements View.OnClickListener{
    /**记录按下返回键的时间*/
    private long mLastBackPressedTime = 0;
    /**两次back键间隔时间*/
    private final static long TIME_INTERVAL = 1000;
    @BindView(R.id.home_page_toolbar) Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
    }

    /**
     * 初始化布局文件 view等
     * */
    private void initView(){
        ButterKnife.bind(this);
        setToolbar();
        mDrawerLayout = findViewById(R.id.drawer_layout_left);
        setDrawerLayout();
    }

    /**
     * 设置左边侧滑栏
     * */
    private void setDrawerLayout(){
        /**侧滑栏监听器*/
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_layout_open, R.string.drawer_layout_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toasty.info(HomePageActivity.this,"Open Drawer").show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toasty.info(HomePageActivity.this,"Close Drawer").show();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setToolbar(){
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                /**返回按钮可用*/
                actionBar.setHomeButtonEnabled(true);
                /**显示返回图标*/
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
            Toasty.info(this,"Press again to exit.").show();
        }
    }
}
