package com.lipeng.mygithub.http.api;

import com.lipeng.mygithub.bean.User;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * 用户接口，用于获取用户个人信息
 * @author lipeng-ds3
 * @date 2018/8/10.
 */

public interface UserService {

    /**
     * 获取当前用户信息
     * @param forceNetwork 请求头，是否强制使用网络
     * @return
     * */
    @GET("user")
    Observable<Response<User>> getUserInfo(@Header("forceNetwork") boolean forceNetwork);
}
