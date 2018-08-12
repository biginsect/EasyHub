package com.lipeng.mygithub.ui.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.lipeng.mygithub.app.AppConfig;
import com.lipeng.mygithub.bean.response.AuthResponse;
import com.lipeng.mygithub.http.api.LoginService;
import com.lipeng.mygithub.http.base.GitHubRetrofit;
import com.lipeng.mygithub.base.MvpBasePresenter;
import com.lipeng.mygithub.bean.response.OAuthToken;
import com.lipeng.mygithub.http.base.HttpObserver;
import com.lipeng.mygithub.http.base.HttpResponse;
import com.lipeng.mygithub.http.base.HttpSubscriber;
import com.lipeng.mygithub.ui.contract.ILoginContract;
import com.lipeng.mygithub.bean.IUser;
import com.lipeng.mygithub.bean.UserModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * 登录presenter实现类
 * @author biginsect
 * @date 2017/12/28
 */

public class LoginPresenter extends MvpBasePresenter<ILoginContract.ILoginView>
        implements ILoginContract.ILoginPresenter {
    private IUser IUser;
    private Handler handler;

    public LoginPresenter(){
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 初始化User
     */
    private void initUser(){
        /*应该要获取github的授权检验用户名与密码*/
        IUser = new UserModel("123","123");
    }

    @Override
    public void login(String name, String password) {
        if (isViewAttached()) {
            final boolean result = IUser.checkLogin(name, password);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    /**有进度条的显示与隐藏，需要使用handler操作*/
                    getView().onLoginResult(result, "0");
                }
            });
        }
    }

    @Override
    public void getToken(@NotNull Intent intent) {
        Uri uri = intent.getData();
        if (null != uri){
            String code = uri.getQueryParameter("code");
            String state = uri.getQueryParameter("state");
            getToken(code, state);
        }
    }

    /**
     * @param code 登录后跳转至github授权网页，需要用户授权并返回参数code
     * @param state 随机字符串
     * */
    private void getToken(String code, String state){
        Observable<Response<OAuthToken>> observable = getLoginService()
                .getAccessToken(AppConfig.CLIENT_ID, AppConfig.CLIENT_SECRET, code, state);

        HttpSubscriber<OAuthToken> subscriber = new HttpSubscriber<>(
                new HttpObserver<OAuthToken>() {
                    @Override
                    public void onError(@Nullable Throwable error) {

                    }

                    @Override
                    public void onSuccess(@Nullable HttpResponse<OAuthToken> response) {
                        OAuthToken authToken = response.body();
                        if (isViewAttached()){
                            if (null != authToken){
                                getView().getTokenSuccess(AuthResponse.createUseToken(authToken));
                            }else {
                                getView().getTokenFailed(response.getOriginalResponse().message());
                            }
                        }
                    }
                }
        );

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<OAuthToken>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<OAuthToken> oAuthTokenResponse) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * retrofit创建
     * */
    private LoginService getLoginService(){
        return GitHubRetrofit.INSTANCE
                .createRetrofit(AppConfig.GIT_HUB_BASE_URL, null)
                .create(LoginService.class);
    }

    private LoginService getLoginService(String token){
        return GitHubRetrofit.INSTANCE
                .createRetrofit(AppConfig.BASE_API_URL, token)
                .create(LoginService.class);
    }
}
