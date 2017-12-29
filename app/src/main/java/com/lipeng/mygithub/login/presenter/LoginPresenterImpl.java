package com.lipeng.mygithub.login.presenter;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.lipeng.mygithub.login.view.LoginView;
import com.lipeng.mygithub.login.model.User;
import com.lipeng.mygithub.login.model.UserModel;

import javax.inject.Inject;

/**
 * 登录presenter实现类
 * @author lipeng
 * @date 2017/12/28
 */

public class LoginPresenterImpl implements LoginPresenter{
    private LoginView loginView;
    private User user;
    private Handler handler;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 初始化User
     */
    private void initUser(){
        /*应该要获取github的授权检验用户名与密码*/
        user = new UserModel("123","123");
    }

    @Override
    public void login(String name, String password) {
        final boolean result = user.checkLogin(name, password);
        handler.post(new Runnable() {
            @Override
            public void run() {
                loginView.onLoginResult(result, "0");
            }
        });
    }

    /**
     * 在这里关闭某些资源
     * */
    @Override
    public void onDestroy() {
        loginView = null;
    }

    /**
     * 进度条的显示
     * */
    @Override
    public void setProgressBarVisibility(int visibility) {
        loginView.onSetProgressBar(visibility);
    }

    /**
     * 隐藏软键盘
     * */
    @Override
    public void hideSoftKeyboard(View view) {
        loginView.onHideSoftKeyboard(view);
    }
}
