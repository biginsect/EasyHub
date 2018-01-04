package com.lipeng.mygithub.login.view;

import android.view.View;

/**
 * 登录视图层
 * @author lipeng
 * @date 2017/12/28
 */

public interface LoginView {
    /**
     * 登录结果回调
     * @param result 成功 true，失败false
     * @param code 标识码
     * */
    void onLoginResult(boolean result, String code);

    /**
     * 回调，隐藏软键盘
     *@param view target
     * */
    void onHideSoftKeyboard(View view);

    /**
     * 显示进度条
     * @param visibility 是否可见
     * */
    void onSetProgressBar(int visibility);

    /**
     * 页面跳转回调
     * */
    void onSkipPage();
}
