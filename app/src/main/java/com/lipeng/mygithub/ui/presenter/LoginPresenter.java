package com.lipeng.mygithub.ui.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;

import com.lipeng.mygithub.base.mvp.MvpBasePresenter;
import com.lipeng.mygithub.ui.contract.ILoginContract;
import com.lipeng.mygithub.bean.User;
import com.lipeng.mygithub.bean.UserModel;

/**
 * 登录presenter实现类
 * @author biginsect
 * @date 2017/12/28
 */

public class LoginPresenter extends MvpBasePresenter<ILoginContract.ILoginView>
        implements ILoginContract.ILoginPresenter {
    private User user;
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
        user = new UserModel("123","123");
    }

    @Override
    public void login(String name, String password) {
        if (isViewAttached()) {
            final boolean result = user.checkLogin(name, password);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    /**有进度条的显示与隐藏，需要使用handler操作*/
                    getView().onLoginResult(result, "0");
                }
            });
        }
    }

    /**
     * 隐藏软键盘
     * */
    @Override
    public void hideSoftKeyboard(@NonNull View view) {
        if (isViewAttached()) {
            getView().onHideSoftKeyboard(view);
        }
    }

    @Override
    public void jump() {
        if (isViewAttached()){
            getView().onJump();
        }
    }
}
