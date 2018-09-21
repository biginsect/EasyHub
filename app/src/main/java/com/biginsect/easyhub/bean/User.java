package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author biginsect
 * @date 2018/8/8
 */

public class User implements Parcelable{

    public enum UserType{
        User, Organiazation
    }

    private String login;
    private String id;
    private String name;
    @SerializedName("avatar_url") private String avatarUrl;
    @SerializedName("html_url") private String htmlUrl;
    private UserType type;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String bio;
    @SerializedName("public_repos") private int publicRepos;
    @SerializedName("public_gists") private int publicGists;
    private int followers;
    private int following;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;

    public User(){

    }

    protected User(Parcel in) {
        login = in.readString();
        id = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        htmlUrl = in.readString();
        int tmpType = in.readInt();
        type = tmpType == -1 ? null : UserType.values()[tmpType];
        company = in.readString();
        blog = in.readString();
        location = in.readString();
        email = in.readString();
        bio = in.readString();
        publicRepos = in.readInt();
        publicGists = in.readInt();
        followers = in.readInt();
        following = in.readInt();
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdateAt = in.readLong();
        updatedAt = tmpUpdateAt == -1 ? null : new Date(tmpUpdateAt);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeString(htmlUrl);
        dest.writeInt(type == null ? -1 : type.ordinal());
        dest.writeString(company);
        dest.writeString(blog);
        dest.writeString(location);
        dest.writeString(email);
        dest.writeString(bio);
        dest.writeInt(publicRepos);
        dest.writeInt(publicGists);
        dest.writeInt(followers);
        dest.writeInt(following);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);

    }

    @Override
    public int describeContents() {
        return 0;
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
    public boolean equals(Object obj) {
        if (null != obj && obj instanceof User){
            User user = (User)obj;
            return user.getLogin().equals(login);
        }

        return super.equals(obj);
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
    }

    public int getPublicGists() {
        return publicGists;
    }

    public void setPublicGists(int publicGists) {
        this.publicGists = publicGists;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
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

    public boolean isUser(){
        return UserType.User.equals(type);
    }
}

/**
 "login": "octocat",
 "id": 1,
 "node_id": "MDQ6VXNlcjE=",
 "avatar_url": "https://github.com/images/error/octocat_happy.gif",
 "gravatar_id": "",
 "url": "https://api.github.com/users/octocat",
 "html_url": "https://github.com/octocat",
 "followers_url": "https://api.github.com/users/octocat/followers",
 "following_url": "https://api.github.com/users/octocat/following{/other_user}",
 "gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
 "starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
 "subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
 "organizations_url": "https://api.github.com/users/octocat/orgs",
 "repos_url": "https://api.github.com/users/octocat/repos",
 "events_url": "https://api.github.com/users/octocat/events{/privacy}",
 "received_events_url": "https://api.github.com/users/octocat/received_events",
 "type": "User",
 "site_admin": false,
 "name": "monalisa octocat",
 "company": "GitHub",
 "blog": "https://github.com/blog",
 "location": "San Francisco",
 "email": "octocat@github.com",
 "hireable": false,
 "bio": "There once was...",
 "public_repos": 2,
 "public_gists": 1,
 "followers": 20,
 "following": 0,
 "created_at": "2008-01-14T04:33:35Z",
 "updated_at": "2008-01-14T04:33:35Z"
 */