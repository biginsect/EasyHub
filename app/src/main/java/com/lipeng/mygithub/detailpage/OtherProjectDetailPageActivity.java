package com.lipeng.mygithub.detailpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.base.BaseActivity;
import com.lipeng.mygithub.constant.MessageType;
import com.lipeng.mygithub.detailpage.presenter.DetailPagePresenter;
import com.lipeng.mygithub.detailpage.presenter.DetailPagePresenterImpl;
import com.lipeng.mygithub.detailpage.view.DetailPageView;
import com.lipeng.mygithub.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 点击主页面的列表子项跳转的页面，具体展示该项目的相关信息
 * @author lipeng
 * @date 2018/1/2
 */

public class OtherProjectDetailPageActivity extends BaseActivity implements DetailPageView,
        Toolbar.OnMenuItemClickListener{

    private DetailPagePresenter mPresenter;

    @BindView(R.id.detail_page_toolbar)Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_project_detail_page);
        initView();
    }

    @Override
    protected void initView() {
        mPresenter = new DetailPagePresenterImpl(this);
        ButterKnife.bind(this);
        setToolbar();
    }

    /**
     * 设置标题栏
     * */
    private void setToolbar(){
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            mToolbar.setOnMenuItemClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.other_project_page_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.star:
                mPresenter.star();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * star该项目，更改标识
     * */
    @Override
    public void onStar() {
        ToastUtils.showShortToast(this,"star", MessageType.INFO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    /**
     * 跳转到此页面
     * @param context 上个页面的上下文
     * */
    public static void skip(Context context){
        Intent intent = new Intent(context, OtherProjectDetailPageActivity.class);
        context.startActivity(intent);
    }
}
