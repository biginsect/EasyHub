package com.lipeng.mygithub.login.view;

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
    void loginResult(boolean result, String code);
}
