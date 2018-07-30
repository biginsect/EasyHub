package com.lipeng.mygithub.bean;

/**
 * 用户模型接口
 * @author biginsect
 * @date 2017/12/28
 */

public interface User {

    /**获取用户名
     * @return 用户名
     * */
    String getUserName();

    /**获取密码
     * @return 密码*/
    String getPassword();

    /**
     * 检查用户名与密码是否正确
     * @param pwd 用户名
     * @param userName 密码
     * @return <code>true</code> 全部正确， <code>false</code> 错误
     * */
    boolean checkLogin(String userName, String pwd);
}
