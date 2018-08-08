package com.lipeng.mygithub.bean;

/**
 * 用户实体
 * @author biginsect
 * @date 2017/12/28
 */

public class UserModel implements IUser {
    private String userName;
    private String pwd;

    public UserModel(String userName, String pwd){
        this.userName = userName;
        this.pwd = pwd;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public boolean checkLogin(String userName, String pwd) {
        return (this.userName.equals(userName) && (this.pwd.equals(pwd)));
    }
}
