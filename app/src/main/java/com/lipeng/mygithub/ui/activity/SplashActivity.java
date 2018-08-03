package com.lipeng.mygithub.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lipeng.mygithub.base.BaseMvpActivity;
import com.lipeng.mygithub.ui.contract.ISplashContract;
import com.lipeng.mygithub.ui.presenter.SplashPresenter;

/**
 * app启动之后首先启动的页面，为了防止出现白屏
 *@author biginsect
 */

public class SplashActivity extends BaseMvpActivity<ISplashContract.ISplashView, ISplashContract.ISplashPresenter>
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
    public void showMainPage() {
        finishDelay(1000);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }
}
