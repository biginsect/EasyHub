package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 主页列表显示内容，主要包含用户用字，头像，更新时间以及对应的更新event
 * @author biginsect
 * @date 2017/12/26
 */

public class UserEvents implements Parcelable{
    private String id;
    private User actor;
    @SerializedName("created_at") private Date createdAt;

    public UserEvents(){

    }

    protected UserEvents(Parcel in) {
        id = in.readString();
        actor = in.readParcelable(User.class.getClassLoader());
        long tmpCreateAt = in.readLong();
        this.createdAt = tmpCreateAt == -1 ? null : new Date(tmpCreateAt);
    }

    public static final Creator<UserEvents> CREATOR = new Creator<UserEvents>() {
        @Override
        public UserEvents createFromParcel(Parcel in) {
            return new UserEvents(in);
        }

        @Override
        public UserEvents[] newArray(int size) {
            return new UserEvents[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(actor, flags);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
