package com.biginsect.easygithub.dao;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author biginsect
 * @date  2018/8/8.
 */

@Entity
public class AuthUser implements Parcelable{
    @Id
    @NotNull
    private String accessToken;

    @NotNull
    private Date authTime;
    private int expireIn;

    @NotNull
    private String scope;
    private boolean selected;

    @NotNull
    private String loginId;
    private String name;
    private String avatar;

    protected AuthUser(Parcel in) {
        accessToken = in.readString();
        expireIn = in.readInt();
        scope = in.readString();
        selected = in.readByte() != 0;
        loginId = in.readString();
        name = in.readString();
        avatar = in.readString();
        long tmpAuthTime = in.readLong();
        authTime = tmpAuthTime == -1 ? null : new Date(tmpAuthTime);
    }

    @Generated(hash = 1468666618)
    public AuthUser(@NotNull String accessToken, @NotNull Date authTime,
            int expireIn, @NotNull String scope, boolean selected,
            @NotNull String loginId, String name, String avatar) {
        this.accessToken = accessToken;
        this.authTime = authTime;
        this.expireIn = expireIn;
        this.scope = scope;
        this.selected = selected;
        this.loginId = loginId;
        this.name = name;
        this.avatar = avatar;
    }

    @Generated(hash = 1740224645)
    public AuthUser() {
    }

    public static final Creator<AuthUser> CREATOR = new Creator<AuthUser>() {
        @Override
        public AuthUser createFromParcel(Parcel in) {
            return new AuthUser(in);
        }

        @Override
        public AuthUser[] newArray(int size) {
            return new AuthUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessToken);
        dest.writeInt(expireIn);
        dest.writeString(scope);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(loginId);
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeLong(authTime != null ? authTime.getTime() : -1);
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getAuthTime() {
        return this.authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public int getExpireIn() {
        return this.expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
