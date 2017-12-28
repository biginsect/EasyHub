package com.lipeng.mygithub.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lipeng.mvp.BasePresenter;
import com.lipeng.mvp.MvpView;

/**
 * activity基类，用于对所有activity的统一处理
 * @author lipeng
 * @date 2017/12/26
 */

public class BaseActivity extends AppCompatActivity implements MvpView{
    private BasePresenter mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasePresenter = new BasePresenter();
        mBasePresenter.attachView(this);
    }

}
