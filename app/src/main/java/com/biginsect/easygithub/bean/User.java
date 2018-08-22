package com.biginsect.easygithub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author biginsect
 * @date 2018/8/8
 */

public class User implements Parcelable{

    public User(){

    }

    /**区分个人和组织*/
    public static final int TYPE_USER = -1;
    public static final int TYPE_ORGANIZATION = -2;


    private String login;
    private String id;
    private String name;
    private String company;
    private String location;
    private String email;
    private int type;
    @SerializedName("html_url") private String htmlUrl;
    @SerializedName("avatar_url")private String avatarUrl;
    @SerializedName("followers_url") private String followersUrl;
    @SerializedName("following_url") private String followingUrl;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;
    @SerializedName("repos_url") private String reposUrl;
    @SerializedName("starred_url") private String starredUrl;

    protected User(Parcel in) {
        login = in.readString();
        id = in.readString();
        name = in.readString();
        company = in.readString();
        location = in.readString();
        email = in.readString();
        type = in.readInt();
        htmlUrl = in.readString();
        avatarUrl = in.readString();
        followersUrl = in.readString();
        followingUrl = in.readString();
        reposUrl = in.readString();
        starredUrl = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1? null : new Date(tmpUpdatedAt);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(company);
        dest.writeString(location);
        dest.writeString(email);
        dest.writeInt(type);
        dest.writeString(htmlUrl);
        dest.writeString(avatarUrl);
        dest.writeString(followersUrl);
        dest.writeString(followingUrl);
        dest.writeString(reposUrl);
        dest.writeString(starredUrl);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime(): -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime(): -1);
    }

    public boolean isUser(){
        return TYPE_USER == type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
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

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public String getStarredUrl() {
        return starredUrl;
    }

    public void setStarredUrl(String starredUrl) {
        this.starredUrl = starredUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (null != obj && obj instanceof User){
            User user = (User)obj;
            return user.getLogin().equals(login);
        }

        return super.equals(obj);
    }
}
