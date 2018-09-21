package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Release
 * @author biginsect
 * @date 2018/9/21.
 */


public class Release implements Parcelable{

    private String url;
    @SerializedName("html_url") private String htmlUrl;
    @SerializedName("assets_url") private String assetsUrl;
    @SerializedName("upload_url") private String uploadUrl;
    @SerializedName("tarball_url") private String tarballUrl;
    @SerializedName("zipball_url") private String zipballUrl;
    private String id;
    @SerializedName("node_id") private String nodeId;
    @SerializedName("tag_name") private String tagName;
    @SerializedName("target_commitish") private String targetCommitish;
    private String name;
    private String body;
    private boolean draft;
    @SerializedName("prerelease") private boolean preRelease;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("published_at") private Date publishedAt;
    private User author;
    private ArrayList<ReleaseAsset> assets;

    protected Release(Parcel in) {
        url = in.readString();
        htmlUrl = in.readString();
        assetsUrl = in.readString();
        uploadUrl = in.readString();
        tarballUrl = in.readString();
        zipballUrl = in.readString();
        id = in.readString();
        nodeId = in.readString();
        tagName = in.readString();
        targetCommitish = in.readString();
        name = in.readString();
        body = in.readString();
        draft = in.readByte() != 0;
        preRelease = in.readByte() != 0;
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpPublishedAt = in.readLong();
        publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
        author = in.readParcelable(User.class.getClassLoader());
        assets = in.createTypedArrayList(ReleaseAsset.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(htmlUrl);
        dest.writeString(assetsUrl);
        dest.writeString(uploadUrl);
        dest.writeString(tarballUrl);
        dest.writeString(zipballUrl);
        dest.writeString(id);
        dest.writeString(nodeId);
        dest.writeString(tagName);
        dest.writeString(targetCommitish);
        dest.writeString(name);
        dest.writeString(body);
        dest.writeByte((byte) (draft ? 1 : 0));
        dest.writeByte((byte)(preRelease ? 1 : 0));
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(publishedAt != null ? publishedAt.getTime() : -1);
        dest.writeParcelable(author, flags);
        dest.writeTypedList(assets);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Release> CREATOR = new Creator<Release>() {
        @Override
        public Release createFromParcel(Parcel in) {
            return new Release(in);
        }

        @Override
        public Release[] newArray(int size) {
            return new Release[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getAssetsUrl() {
        return assetsUrl;
    }

    public void setAssetsUrl(String assetsUrl) {
        this.assetsUrl = assetsUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getTarballUrl() {
        return tarballUrl;
    }

    public void setTarballUrl(String tarballUrl) {
        this.tarballUrl = tarballUrl;
    }

    public String getZipballUrl() {
        return zipballUrl;
    }

    public void setZipballUrl(String zipballUrl) {
        this.zipballUrl = zipballUrl;
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTargetCommitish() {
        return targetCommitish;
    }

    public void setTargetCommitish(String targetCommitish) {
        this.targetCommitish = targetCommitish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ArrayList<ReleaseAsset> getAssets() {
        return assets;
    }

    public void setAssets(ArrayList<ReleaseAsset> assets) {
        this.assets = assets;
    }
}
