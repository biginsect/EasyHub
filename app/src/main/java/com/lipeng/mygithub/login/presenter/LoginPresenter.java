package com.lipeng.mygithub.login.presenter;

import android.view.View;

/**
 * 登录模块的presenter
 * @author lipeng
 * @date 2017/12/28
 */

public interface LoginPresenter {
    /**
     * 登录
     * @param name 用户名
     * @param password 密码
     * */
    void login(String name, String password);

    /**
     * 进度条的显示与隐藏
     * @param visibility 可见与否
     * */
    void setProgressBarVisibility(int visibility);

    /**
     * 隐藏软键盘
     * @param view target
     * */
    void hideSoftKeyboard(View view);

    /**
     * 销毁资源
     * */
    void destroy();

    /**
     * 页面跳转
     * */
    void skipPage();
}
