package com.lipeng.mygithub.http.api;

import android.support.annotation.NonNull;

import com.lipeng.mygithub.bean.request.AuthRequest;
import com.lipeng.mygithub.bean.response.AuthResponse;
import com.lipeng.mygithub.bean.response.OAuthToken;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 登录接口
 * @author bininsect
 * @date  2018/8/9.
 */

public interface LoginService {

    /**
     * 鉴权
     * @param authRequest 认证需要的数据
     * @return 鉴权后返回token等内容
     * */
    @POST("login/oauth/authorize")
    @Headers("Accept: application/json")
    Observable<Response<AuthResponse>> authorize(@NonNull @Body AuthRequest authRequest);

    /**
     * 鉴权之后获取令牌(唯一认证)
     * @param clientId register 应用之后返回的id
     * @param clientSecret register 之后返回的secret
     * @param code
     * @param state
     * @return
     * */
    @GET("login/oauth/access_token")
    @Headers("Accept: application/json")
    Observable<Response<OAuthToken>> getAccessToken(
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("code") String code,
            @Query("state") String state
    );
}
