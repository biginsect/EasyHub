package com.lipeng.mygithub.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


/**
 * activity基类，用于对所有activity的统一处理
 * @author lipeng
 * @date 2017/12/26
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 在此初始化布局文件，资源等等
     * */
    protected abstract void initView();
}
