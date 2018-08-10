package com.lipeng.mygithub.base;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;

import com.lipeng.mygithub.app.AppData;
import com.lipeng.mygithub.base.mvp.MvpPresenter;
import com.lipeng.mygithub.base.mvp.MvpView;
import com.lipeng.mygithub.http.api.UserService;
import com.lipeng.mygithub.http.base.GitHubRetrofit;
import com.lipeng.mygithub.app.AppConfig;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;



/**
 * @author biginsect
 * @date 2018/7/30
 */

public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private WeakReference<V> viewRef;

    @UiThread
    @Override
    public void attachView(@Nullable V view) {
        this.viewRef = new WeakReference<>(view);
    }

    @UiThread
    public V getView(){
        return this.viewRef == null? null : this.viewRef.get();
    }

    @UiThread
    protected boolean isViewAttached(){
        return this.viewRef != null && this.viewRef.get() != null;
    }

    @Override
    public void detachView(boolean isDetach) {
        if (null != this.viewRef){
            this.viewRef.clear();
            this.viewRef = null;
        }
    }

    private <T> T getService(Class<T> clazz, String baseUrl, boolean isJson){
        return GitHubRetrofit.INSTANCE
                .createRetrofit(baseUrl, AppData.getAccessToken(),isJson)
                .create(clazz);
    }

    /**
     * 生成retrofit
     * */
    protected <T> T getService(Class<T> clazz){
        return getService(clazz, AppConfig.BASE_API_URL, true);
    }

    /**
     * 用户信息相关的接口retrofit
     * */
    protected UserService getUserService(){
        return getService(UserService.class);
    }

    /**
     * 需要在{@link #isViewAttached() }后调用，否则可能报NPE
     * */
    protected Context getContext(){
        if (getView() instanceof Context){
            return (Context) getView();
        }else if (getView() instanceof Fragment){
            return ((Fragment) getView()).getContext();
        }else {
            throw new NullPointerException("MvpBasePresenter: getView is not instance of Context, cannot invoke getContext()");
        }
    }
}
