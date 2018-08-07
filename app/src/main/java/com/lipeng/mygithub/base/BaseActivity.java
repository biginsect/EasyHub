package com.lipeng.mygithub.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lipeng.mygithub.ui.activity.LoginPageActivity;
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
        initActivity();
        initView(savedInstanceState);
        ActivitiesManager.INSTANCE.addActivity(this);
    }


    /**
     * 获取布局文件id
     * @return 布局文件id
     * */
    protected abstract int getLayoutId();


    /**
     * 初始化布局
     * @param savedInstanceState
     * */
    @CallSuper
    protected void initView(Bundle savedInstanceState){

    }

    /**
     * 初始化activity
     * */
    @CallSuper
    protected void initActivity(){

    }

    /**
     * 设置 toolbar 返回键
     * */
    protected void setToolbarBackAvailable(){
        if (null != getSupportActionBar()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

    /**
     * 延迟销毁页面
     * @param mill 毫秒
     * */
    protected void finishDelay(int mill){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, mill);
    }

    /**
     * 跳转至登录页面
     * */
    protected void showLoginPage(){
        /**关闭当前activity栈中的所有activity，若所有activity都是用default启动方式，则退出app*/
        getActivity().finishAffinity();
        LoginPageActivity.show(getActivity());
    }

    /**
     * 隐藏软键盘
     * @param view target
     * */
    protected void hideSoftKeyboard(@NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
