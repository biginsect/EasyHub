package com.biginsect.easygithub.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;

import com.biginsect.easygithub.R;
import com.biginsect.easygithub.app.AppConfig;
import com.biginsect.easygithub.app.AppData;
import com.biginsect.easygithub.dao.DaoSession;
import com.biginsect.easygithub.dao.GreenDaoManager;
import com.biginsect.easygithub.http.api.UserService;
import com.biginsect.easygithub.http.base.GitHubRetrofit;
import com.biginsect.easygithub.http.base.HttpSubscriber;
import com.biginsect.easygithub.http.error.HttpError;
import com.biginsect.easygithub.util.BlankUtils;
import com.thirtydegreesray.dataautoaccess.DataAutoAccess;

import org.apache.http.conn.ConnectTimeoutException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * @author biginsect
 * @date 2018/8/23.
 */

public abstract class BasePresenter<V extends IBaseContract.IView> implements IBaseContract.IPresenter<V> {

    private WeakReference<V> viewRef;
    /**管理disposable*/
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected DaoSession daoSession;

    @Override
    public void attachView(@Nullable V v) {
        this.viewRef = new WeakReference<>(v);
        daoSession = GreenDaoManager.getInstance().getDaoSession();
    }

    @UiThread
    public V getView(){
        return this.viewRef == null ? null : viewRef.get();
    }

    @UiThread
    protected boolean isViewAttached(){
        return this.viewRef != null && this.viewRef.get() != null;
    }

    @Override
    public void detachView(boolean isDetach) {
        if (null != viewRef){
            viewRef.clear();
            viewRef = null;
        }

        clearDisposables();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        DataAutoAccess.saveData(this, outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {
        if (null != outState){
            DataAutoAccess.getData(this, outState);
        }
    }

    private <T> T getService(Class<T> clazz, String baseUrl, boolean isJson){
        return GitHubRetrofit.INSTANCE
                .createRetrofit(baseUrl, AppData.INSTANCE.getAccessToken(),isJson)
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
    protected UserService getUserService(String token){
        return GitHubRetrofit.INSTANCE
                .createRetrofit(AppConfig.BASE_API_URL,token)
                .create(UserService.class);
    }

    protected UserService getUserService(){
        return getUserService(AppData.INSTANCE.getAccessToken());
    }

    /**
     * 执行RX请求
     * */
    protected <T> void executeRxHttp(@NonNull Observable<Response<T>> observable,
                                     @NonNull HttpSubscriber<T> subscriber){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @NotNull
    @Override
    public Context getContext() {
        if (getView() instanceof Context){
            return (Context) getView();
        }else if (getView() instanceof Fragment){
            return ((Fragment) getView()).getContext();
        }else {
            throw new NullPointerException("BasePresenter: getView is not instance of Context, cannot invoke getContext()");
        }
    }

    protected String getString(@StringRes int resId){
        return getContext().getResources().getString(resId);
    }

    @NonNull
    protected String getErrorMsg(Throwable throwable){
        String msg = "";
        if (null == throwable){
            return msg;
        }

        if (throwable instanceof UnknownHostException){
            msg = getString(R.string.no_network_msg);
        }else if (throwable instanceof SocketTimeoutException || throwable instanceof ConnectTimeoutException){
            msg = getString(R.string.time_out_msg);
        }else if (throwable instanceof HttpError){
            msg = throwable.getMessage();
        }else {
            msg = BlankUtils.INSTANCE.isBlankString(throwable.getMessage()) ? throwable.toString():throwable.getMessage();
        }

        return msg;
    }

    protected void registerDisposable(Disposable disposable){
        mCompositeDisposable.add(disposable);
    }

    private void clearDisposables(){
        if (null != mCompositeDisposable){
            mCompositeDisposable.clear();
        }
    }
}
