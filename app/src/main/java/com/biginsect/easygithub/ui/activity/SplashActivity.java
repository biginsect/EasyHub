package com.biginsect.easygithub.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.biginsect.easygithub.ui.base.BaseActivity;
import com.biginsect.easygithub.ui.contract.ISplashContract;
import com.biginsect.easygithub.ui.presenter.SplashPresenter;


/**
 * app启动之后首先启动的页面，为了防止出现白屏
 *@author biginsect
 */

public class SplashActivity extends BaseActivity<ISplashContract.ISplashView, ISplashContract.ISplashPresenter>
        implements ISplashContract.ISplashView{

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @NonNull
    @Override
    public ISplashContract.ISplashPresenter createPresenter() {
        presenter = new SplashPresenter();
        return presenter;
    }

    @Override
    protected void initActivity() {
        super.initActivity();
        presenter.getUser();
    }

    @Override
    public void showHomePage() {
        finishDelay();
        Uri uri = getIntent().getData();
        if (null != uri) {
            startActivity(new Intent(getActivity(), HomePageActivity.class));
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }
}
