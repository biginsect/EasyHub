package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author lipeng-ds3
 * @date 2018/9/21.
 */


public class Label implements Parcelable{

    private long id;
    private String name;
    private String color;
    @SerializedName("default") private boolean isDefault;
    private boolean select;

    public Label(){

    }

    protected Label(Parcel in) {
        id = in.readLong();
        name = in.readString();
        color = in.readString();
        isDefault = in.readByte() != 0;
        select = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(color);
        dest.writeByte((byte) (isDefault ? 1 : 0));
        dest.writeByte((byte) (select ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Label> CREATOR = new Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel in) {
            return new Label(in);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Label){
            Label label = (Label) obj;
            return label.getId() == id;
        }
        return super.equals(obj);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}

/*
{
    "id": 208045946,
    "node_id": "MDU6TGFiZWwyMDgwNDU5NDY=",
    "url": "https://api.github.com/repos/octocat/Hello-World/labels/bug",
    "name": "bug",
    "description": "Houston, we have a problem",
    "color": "f29513",
    "default": true
  }
 */
