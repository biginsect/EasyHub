package com.biginsect.easygithub.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.biginsect.easygithub.app.AppData;
import com.biginsect.easygithub.mvp.BaseMvpActivity;
import com.biginsect.easygithub.constant.ToastType;
import com.biginsect.easygithub.ui.activity.LoginPageActivity;
import com.biginsect.easygithub.ui.activity.SplashActivity;
import com.biginsect.easygithub.util.ActivitiesManager;
import com.biginsect.easygithub.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.thirtydegreesray.dataautoaccess.DataAutoAccess;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;

/**
 * @author biginsect
 * @date 2018/8/23.
 */

public abstract class BaseActivity<V extends IBaseContract.IView, P extends IBaseContract.IPresenter<V>> extends BaseMvpActivity<V,P>
        implements IBaseContract.IView{
    private final static String TAG = "BaseActivity";
    private static BaseActivity currentActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "--onCreate");
        if (null == AppData.INSTANCE.getAuthUser() || null == AppData.INSTANCE.getLoggedUser()){
            super.onCreate(savedInstanceState);
            finishAffinity();
            startActivity(new Intent(getActivity(), SplashActivity.class));
            return;
        }

        DataAutoAccess.getData(this, savedInstanceState);
        presenter.onRestoreInstanceState(savedInstanceState == null ? getIntent().getExtras(): savedInstanceState);

        if (null != savedInstanceState && null == AppData.INSTANCE.getAuthUser()){
            DataAutoAccess.getData(AppData.INSTANCE, savedInstanceState);
        }

        if (0 != getLayoutId()) {
            setContentView(getLayoutId());
            ButterKnife.bind(getActivity());
        }
        initActivity();
        initView(savedInstanceState);
        ActivitiesManager.INSTANCE.addActivity(this);
    }

    @Override
    protected void onResume() {
        Logger.d(TAG, "---onResume");
        super.onResume();
        currentActivity = getActivity();
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG, "---onDestroy");
        super.onDestroy();
        if (this.equals(currentActivity)) {
            currentActivity =null;
        }
        ActivitiesManager.INSTANCE.removeActivity(this);
    }

    @Override
    public void finish() {
        ActivitiesManager.INSTANCE.removeActivity(this);
        super.finish();
    }

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

    protected void setToolbarBackAvailable(){
        if (null != getSupportActionBar()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setToolbarTitle(String title){
        if (null != getSupportActionBar()){
            getSupportActionBar().setTitle(title);
        }
    }


    @NonNull
    protected BaseActivity getActivity() {
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

    protected void finishDelay(){
        finishDelay(1000);
    }

    protected void hideSoftKeyboard(@NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    @Override
    public void showLoginPage() {
        finishDelay();
        Intent intent = new Intent(getActivity(), LoginPageActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(@NotNull String msg) {
        ToastUtils.INSTANCE.showShortToast(getActivity(), msg, ToastType.ERROR);
    }

    @Override
    public void showInfo(@NotNull String msg) {
        ToastUtils.INSTANCE.showShortToast(getActivity(), msg, ToastType.INFO);
    }

    @Override
    public void showWarning(@NotNull String msg) {
        ToastUtils.INSTANCE.showShortToast(getActivity(), msg, ToastType.WARNING);
    }

    @Override
    public void showSuccess(@NotNull String msg) {
        ToastUtils.INSTANCE.showShortToast(getActivity(), msg, ToastType.SUCCESS);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
