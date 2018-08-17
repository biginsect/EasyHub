package com.lipeng.mygithub.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 鉴权之后返回的数据
 * @author biginsect
 * @date  2018/8/9.
 */

public class AuthToken {
    private int id;
    private String url;
    private String token;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;
    /**获取用户的数据范围*/
    private List<String> scopes;
    /**鉴权之后返回的随机字符串*/
    private String state;

    public static AuthToken createAuthToken(OAuthToken token){
        AuthToken authToken = new AuthToken();
        authToken.setToken(token.getAccessToken());
        authToken.setScopes(Arrays.asList(token.getScope().split(",")));

        return authToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
