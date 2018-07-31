package com.lipeng.mygithub.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lipeng.mygithub.util.ActivitiesManager;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;


/**
 * activity基类，用于对所有activity的统一处理
 * @author biginsect
 * @date 2017/12/26
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "----onCreate()");
        if (0 != getLayoutId()) {
            setContentView(getLayoutId());
            ButterKnife.bind(getActivity());
        }
        initView(savedInstanceState);
        ActivitiesManager.INSTANCE.addActivity(this);
    }


    /**
     * 获取布局文件id
     * @return 布局文件id
     * */
    protected abstract int getLayoutId();


    /**
     * 初始化
     * @param savedInstanceState
     * */
    @CallSuper
    protected void initView(Bundle savedInstanceState){

    }

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

    @NonNull
    protected BaseActivity getActivity(){
        return this;
    }
}
