package com.lipeng.mygithub.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.lipeng.mygithub.util.ActivitiesManager;
import com.orhanobut.logger.Logger;


/**
 * activity基类，用于对所有activity的统一处理
 * @author lipeng
 * @date 2017/12/26
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "----onCreate()");
        setContentView(getLayoutId());
        ActivitiesManager.INSTANCE.addActivity(this);
    }

    /**
     * 在此初始化布局文件，资源等等
     * */
    protected abstract void init();

    /**
     * 获取布局文件id
     * @return 布局文件id
     * */
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG,"----onDestroy()");
        ActivitiesManager.INSTANCE.removeActivity(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.d(TAG,"----onNewIntent()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG,"----onStop()");
    }

    @Override
    public void finish() {
        ActivitiesManager.INSTANCE.removeActivity(this);
        super.finish();
    }
}
