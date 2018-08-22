package com.biginsect.easygithub.bean.request;

import com.google.gson.annotations.SerializedName;
import com.biginsect.easygithub.app.AppConfig;

import java.util.Arrays;
import java.util.List;

/**
 * 获取基础权限，需要验证app_id，重定向的url
 * 以及开发者的client_id & client_secret（可以通过gitHub注册后获得）
 * @author biginsect
 * @date  2018/8/9.
 */

public class AuthRequest {
    private List<String> scopes;
    private String appId;
    private String redirectUrl;
    @SerializedName("client_id") private String clientId;
    @SerializedName("client_secret") private String clientSecret;

    /**
     * 静态工厂方法
     * */
    public static AuthRequest createAuth(){
        AuthRequest request = new AuthRequest();
        request.appId = AppConfig.APPLICATION_ID;
        request.scopes = Arrays.asList("user", "repo", "gist", "notifications");
        request.clientId = AppConfig.CLIENT_ID;
        request.clientSecret = AppConfig.CLIENT_SECRET;
        request.redirectUrl = AppConfig.REDIRECT_URL;

        return request;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public String getAppId() {
        return appId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
