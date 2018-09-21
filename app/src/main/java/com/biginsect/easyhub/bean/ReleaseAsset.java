package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Release中的--Asset
 * @author biginsect
 * @date 2018/9/21.
 */


public class ReleaseAsset implements Parcelable{
    private String url;
    @SerializedName("browser_download_url") private String browserDownloadUrl;
    private String id;
    @SerializedName("node_id") private String nodeId;
    private String name;
    private String label;
    private String state;
    @SerializedName("content_type") private String contentType;
    private long size;
    @SerializedName("download_count") private int downloadCount;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;
    private User uploader;

    public ReleaseAsset(){

    }

    protected ReleaseAsset(Parcel in) {
        url = in.readString();
        browserDownloadUrl = in.readString();
        id = in.readString();
        nodeId = in.readString();
        name = in.readString();
        label = in.readString();
        state = in.readString();
        contentType = in.readString();
        size = in.readLong();
        downloadCount = in.readInt();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        uploader = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(browserDownloadUrl);
        dest.writeString(id);
        dest.writeString(nodeId);
        dest.writeString(name);
        dest.writeString(label);
        dest.writeString(state);
        dest.writeString(contentType);
        dest.writeLong(size);
        dest.writeInt(downloadCount);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeParcelable(uploader, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReleaseAsset> CREATOR = new Creator<ReleaseAsset>() {
        @Override
        public ReleaseAsset createFromParcel(Parcel in) {
            return new ReleaseAsset(in);
        }

        @Override
        public ReleaseAsset[] newArray(int size) {
            return new ReleaseAsset[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBrowserDownloadUrl() {
        return browserDownloadUrl;
    }

    public void setBrowserDownloadUrl(String browserDownloadUrl) {
        this.browserDownloadUrl = browserDownloadUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
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

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
}
