package com.lipeng.mygithub.ui.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.lipeng.mygithub.app.AppConfig;
import com.lipeng.mygithub.app.AppData;
import com.lipeng.mygithub.bean.User;
import com.lipeng.mygithub.bean.response.AuthResponse;
import com.lipeng.mygithub.dao.AuthUser;
import com.lipeng.mygithub.dao.AuthUserDao;
import com.lipeng.mygithub.dao.GreenDaoManager;
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
import com.lipeng.mygithub.util.ListUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        HttpSubscriber<OAuthToken> tokenHttpSubscriber = new HttpSubscriber<>(new HttpObserver<OAuthToken>() {
            @Override
            public void onError(@NotNull Throwable error) {
                if (isViewAttached()){
                    getView().getTokenFailed(error.getMessage());
                }
            }

            @Override
            public void onSuccess(@NotNull HttpResponse<OAuthToken> response) {
                OAuthToken authToken = response.body();
                if (isViewAttached()){
                    if (null != authToken){
                        getView().getTokenSuccess(AuthResponse.createUseToken(authToken));
                    }else {
                        getView().getTokenFailed(response.getOriginalResponse().message());
                    }
                }
            }

            @Override
            public void onSubscribe(@NotNull Disposable d) {
                registerDisposable(d);
            }
        });
        getLoginService().getAccessToken(AppConfig.CLIENT_ID, AppConfig.CLIENT_SECRET, code, state)
                /**耗时操作 io线程*/
                .subscribeOn(Schedulers.io())
                /**接收事件 main，可以更新视图等*/
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tokenHttpSubscriber);
    }

    @Override
    public void getUserInfo(@NotNull final AuthResponse authResponse) {
        HttpSubscriber<User> userHttpSubscriber = new HttpSubscriber<>(
                new HttpObserver<User>() {
            @Override
            public void onError(@NotNull Throwable error) {
                if (isViewAttached()){
                    getView().getTokenFailed(error.getMessage());
                }
            }

            @Override
            public void onSuccess(@NotNull HttpResponse<User> response) {
                User user = response.body();
                if (isViewAttached()){
                    saveUserInfo(authResponse, user);
                    getView().onLoginSuccess();
                }
            }

            @Override
            public void onSubscribe(@NotNull Disposable d) {
                registerDisposable(d);
            }
        });

        getUserService(authResponse.getToken()).getUserInfo(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userHttpSubscriber);
    }

    /**
     * token认证成功，保存用户信息，将当前用户数据记录下来，下次打开app 不需要登录
     * */
    private void saveUserInfo(AuthResponse authResponse, User user){
        String updateUser = "UPDATE " + daoSession.getAuthUserDao().getTablename()
                + " SET " + AuthUserDao.Properties.Selected.columnName + " = 0";
        daoSession.getAuthUserDao().getDatabase().execSQL(updateUser);

        String deleteExists = "DELETE FROM " + daoSession.getAuthUserDao().getTablename()
                + " WHERE " +AuthUserDao.Properties.LoginId.columnName
                + " = " +user.getLogin() + "'";
        daoSession.getAuthUserDao().getDatabase().execSQL(deleteExists);

        AuthUser authUser = new AuthUser();
        String scope = ListUtils.INSTANCE.listToString(authResponse.getScopes());
        Date date = new Date();
        authUser.setAccessToken(authResponse.getToken());
        authUser.setScope(scope);
        authUser.setAuthTime(date);
        //360天
        authUser.setExpireIn(360 * 24 * 60 * 60);
        authUser.setSelected(true);
        authUser.setLoginId(user.getLogin());
        authUser.setName(user.getName());
        authUser.setAvatar(user.getAvatarUrl());
        daoSession.getAuthUserDao().insert(authUser);

        AppData.INSTANCE.setAuthUser(authUser);
        AppData.INSTANCE.setLoggedUser(user);
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
