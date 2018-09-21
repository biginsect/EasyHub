package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * PushEvent中的commits
 * @author biginsect
 * @date 2018/9/21.
 */


public class PushEventCommit implements Parcelable{

    private String sha;
    private User author;
    private String message;
    private boolean distinct;
    private String url;

    public PushEventCommit(){

    }

    protected PushEventCommit(Parcel in) {
        sha = in.readString();
        author = in.readParcelable(User.class.getClassLoader());
        message = in.readString();
        distinct = in.readByte() != 0;
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sha);
        dest.writeParcelable(author, flags);
        dest.writeString(message);
        dest.writeByte((byte) (distinct ? 1 : 0));
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PushEventCommit> CREATOR = new Creator<PushEventCommit>() {
        @Override
        public PushEventCommit createFromParcel(Parcel in) {
            return new PushEventCommit(in);
        }

        @Override
        public PushEventCommit[] newArray(int size) {
            return new PushEventCommit[size];
        }
    };

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
