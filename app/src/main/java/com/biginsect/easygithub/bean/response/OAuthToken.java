package com.biginsect.easygithub.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * 将鉴权之后的response作为参数获取到访问令牌
 * 获取个人stars、相关repository等需要用到令牌
 * @author biginsect
 * @date  2018/8/9.
 */

public class OAuthToken {
    @SerializedName("access_token") private String accessToken;
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
