package com.lipeng.mygithub.ui.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.lipeng.mygithub.app.AppConfig;
import com.lipeng.mygithub.http.api.LoginService;
import com.lipeng.mygithub.http.api.UserService;
import com.lipeng.mygithub.http.base.GitHubRetrofit;
import com.lipeng.mygithub.base.mvp.MvpBasePresenter;
import com.lipeng.mygithub.bean.response.OAuthToken;
import com.lipeng.mygithub.ui.contract.ILoginContract;
import com.lipeng.mygithub.bean.IUser;
import com.lipeng.mygithub.bean.UserModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Response;
import rx.Observable;

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
     * @param state 跳转至授权网页携带的参数
     * */
    private void getToken(String code, String state){
        Observable<Response<OAuthToken>> observable = getLoginService()
                .getAccessToken(AppConfig.CLIENT_ID, AppConfig.CLIENT_SECRET, code, state);

    }

    /**
     * retrofit创建
     * */
    private LoginService getLoginService(){
        return GitHubRetrofit.INSTANCE
                .createRetrofit(AppConfig.GIT_HUB_BASE_URL, null)
                .create(LoginService.class);
    }

    private UserService getUserService(String token){
        return GitHubRetrofit.INSTANCE
                .createRetrofit(AppConfig.BASE_API_URL, token)
                .create(UserService.class);
    }
}
