package com.biginsect.easyhub.ui.presenter.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;

import com.biginsect.easyhub.R;
import com.biginsect.easyhub.app.AppConfig;
import com.biginsect.easyhub.app.AppData;
import com.biginsect.easyhub.dao.DaoSession;
import com.biginsect.easyhub.dao.GreenDaoManager;
import com.biginsect.easyhub.http.api.UserService;
import com.biginsect.easyhub.http.base.GitHubRetrofit;
import com.biginsect.easyhub.http.base.HttpObserver;
import com.biginsect.easyhub.http.base.HttpProgressSubscriber;
import com.biginsect.easyhub.http.base.HttpResponse;
import com.biginsect.easyhub.http.base.HttpSubscriber;
import com.biginsect.easyhub.http.base.IObservableCreator;
import com.biginsect.easyhub.http.error.ErrorCode;
import com.biginsect.easyhub.http.error.HttpError;
import com.biginsect.easyhub.http.error.PageNotFoundException;
import com.biginsect.easyhub.http.error.UnauthorizedException;
import com.biginsect.easyhub.ui.contract.base.IBaseContract;
import com.biginsect.easyhub.util.BlankUtils;
import com.biginsect.easyhub.util.NetUtils;
import com.biginsect.easyhub.util.PreUtils;
import com.thirtydegreesray.dataautoaccess.DataAutoAccess;

import org.apache.http.conn.ConnectTimeoutException;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

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

    protected <T> void executeRxHttp(@NonNull IObservableCreator<T> creator, @NonNull HttpObserver<T> observer){
        executeRxHttp(creator, observer, false);
    }

    protected <T> void executeRxHttp(@NonNull IObservableCreator<T> creator, @NonNull HttpObserver<T> observer,
                                     boolean readCache){
        executeRxHttp(creator, observer, readCache, null);
    }

    private Map<String, Integer>requestTimes = new HashMap<>();

    protected <T> void executeRxHttp(@NonNull final IObservableCreator<T> creator, @NonNull final HttpObserver<T> observer,
                                     final boolean readCache, final ProgressDialog progressDialog){
        requestTimes.put(creator.toString(), 1);

        final HttpObserver<T> tmpObserver = new HttpObserver<T>() {
            @Override
            public void onError(@NonNull Throwable error) {
                if (!isUnauthorized(error)){
                    observer.onError(error);
                }
            }

            @Override
            public void onSuccess(@NonNull HttpResponse<T> response) {
                if (response.isSuccessful()){
                    if (readCache && response.isFromCache() && NetUtils.INSTANCE.isNetworkAvailable()
                            && requestTimes.get(creator.toString()) < 2){
                        requestTimes.put(creator.toString(), 2);
                        executeRxHttp(creator.create(true), getSubscriber(this, progressDialog));
                    }
                    observer.onSuccess(response);
                }else if(response.getOriginalResponse().code() == 404){
                    onError(new PageNotFoundException());
                }else if (response.getOriginalResponse().code() == 504){
                    onError(new HttpError(ErrorCode.CACHE_AND_NETWORK_UNAVAILABLE));
                }else if (response.getOriginalResponse().code() == 401){
                    onError(new UnauthorizedException());
                }else {
                    onError(new Error(response.getOriginalResponse().message()));
                }
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                registerDisposable(d);
            }
        };

        boolean cacheFirstAvailable = PreUtils.INSTANCE.isCacheFirstAvailable();
        executeRxHttp(creator.create(!cacheFirstAvailable || !readCache),
                getSubscriber(tmpObserver, progressDialog));
    }

    private <T> HttpSubscriber<T> getSubscriber(HttpObserver<T> observer, ProgressDialog progressDialog){
        return progressDialog == null ? new HttpSubscriber<>(observer) : new HttpProgressSubscriber<>(progressDialog, observer);
    }

    protected String getLoadingMsg(){
        return getString(R.string.loading).concat("...");
    }

    @NonNull
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

    /**
     * 检查是否为未授权异常
     * */
    private boolean isUnauthorized(Throwable throwable){
        if (throwable instanceof UnauthorizedException){
            if (isViewAttached()){
                getView().showError(throwable.getMessage());
                daoSession.getAuthUserDao().delete(AppData.INSTANCE.getAuthUser());
                AppData.INSTANCE.setAuthUser(null);
                AppData.INSTANCE.setLoggedUser(null);
                getView().showLoginPage();
                return true;
            }
        }

        return false;
    }
}
