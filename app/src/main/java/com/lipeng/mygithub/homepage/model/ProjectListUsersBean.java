package com.lipeng.mygithub.homepage.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * 用于保存其他用户的头像，用户名，最近动态信息的时间与行为动作等
 * 已实现序列化，将数据写入共享内存中，其他进程可以通过Parcel从这个共享内存中读取字节流
 * @author lipeng
 * @date 2017/12/26
 */

public class ProjectListUsersBean implements Parcelable{
    /**保存的用户头像URL*/
    private String otherPictureUrl;
    /**用户名*/
    private String otherName;
    /**该用户更新信息的时间节点（以当前日期为参考），以天为单位，*/
    private int otherUpdateDays;
    /**日期 未实现序列化*/
    private Date otherUpdateDate;
    /**动态信息*/
    private String otherDynamic;

    public ProjectListUsersBean(){
    }

    public ProjectListUsersBean(Parcel in){
        this.otherPictureUrl = in.readString();
        this.otherName = in.readString();
        this.otherUpdateDays = in.readInt();
        this.otherDynamic = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.otherPictureUrl);
        dest.writeString(this.otherName);
        dest.writeInt(this.otherUpdateDays);
//        dest.writeValue(this.otherUpdateDate);
        dest.writeString(this.otherDynamic);
    }

    public static final Parcelable.Creator<ProjectListUsersBean> CREATOR = new Creator<ProjectListUsersBean>() {
        @Override
        public ProjectListUsersBean createFromParcel(Parcel source) {
            return new ProjectListUsersBean(source);
        }

        @Override
        public ProjectListUsersBean[] newArray(int size) {
            return new ProjectListUsersBean[size];
        }
    };

    @Override
    public String toString() {
        return "UserBean{" +
                "imageUrl = '" + otherPictureUrl +"',\n " +
                "userName = '" + otherName +"',\n " +
                "otherUpdateDays = '" + otherUpdateDays +"',\n "+
                "otherDynamic = '" + otherDynamic + "', "+
                "}";
    }
}
