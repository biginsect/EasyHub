package com.lipeng.mygithub.detailpage.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 详情页实体类，实现序列化，允许其他进程在内存中读取
 * @author lipeng
 * @date 2018/1/3
 */

public class UserDetailBean implements Parcelable{
    /**项目介绍*/
    private String projectIntroduction;
    /**star人数*/
    private int starCount;
    /**Fork数*/
    private int forkCount;
    /**watching数*/
    private int watcherCount;

    public UserDetailBean(){

    }

    public UserDetailBean(Parcel in){
        this.projectIntroduction = in.readString();
        this.starCount = in.readInt();
        this.forkCount = in.readInt();
        this.watcherCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectIntroduction);
        dest.writeInt(this.starCount);
        dest.writeInt(this.forkCount);
        dest.writeInt(this.watcherCount);
    }

    public static final Parcelable.Creator<UserDetailBean> CREATOR = new Creator<UserDetailBean>() {
        @Override
        public UserDetailBean createFromParcel(Parcel source) {
            return new UserDetailBean(source);
        }

        @Override
        public UserDetailBean[] newArray(int size) {
            return new UserDetailBean[size];
        }
    };

    @Override
    public String toString() {
        return "projectIntroduction = '" + projectIntroduction + "',\n "+
                "starCount = '" + starCount + "',\n" +
                "forkCount = '" + forkCount + "',\n" +
                "watcherCount = '" + watcherCount + "'";
    }
}
