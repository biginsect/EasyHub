package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * repository相关权限
 * @author biginsect
 * @date 2018/9/21.
 */


public class RepositoryPermissions implements Parcelable{

    private boolean admin;
    private boolean push;
    private boolean pull;

    public RepositoryPermissions(){

    }

    protected RepositoryPermissions(Parcel in) {
        admin = in.readByte() != 0;
        push = in.readByte() != 0;
        pull = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (admin ? 1 : 0));
        dest.writeByte((byte) (push ? 1 : 0));
        dest.writeByte((byte) (pull ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RepositoryPermissions> CREATOR = new Creator<RepositoryPermissions>() {
        @Override
        public RepositoryPermissions createFromParcel(Parcel in) {
            return new RepositoryPermissions(in);
        }

        @Override
        public RepositoryPermissions[] newArray(int size) {
            return new RepositoryPermissions[size];
        }
    };

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public boolean isPull() {
        return pull;
    }

    public void setPull(boolean pull) {
        this.pull = pull;
    }
}
