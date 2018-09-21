package com.biginsect.easyhub.http.api;

import com.biginsect.easyhub.bean.User;
import com.biginsect.easyhub.bean.Event;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * 用户接口，用于获取用户个人信息
 * @author biginsect
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

    /**
     * events 列表，主页展示内容
     * */
    @GET("events")
    Observable<Response<ArrayList<Event>>> getEvents(
            @Header("forceNetwork") boolean forceNetwork,
            @Query("page") int page
    );
}
