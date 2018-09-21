package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * https://developer.github.com/v3/issues/
 * @author lipeng-ds3
 * @date 2018/9/21.
 */


public class Issue implements Parcelable{

    public enum IssueState{
        open, closed
    }

    /*public enum IssueAuthorAssociation{
        OWNER, CONTRIBUTOR, NONE
    }*/

    private String id;
    private int number;
    private String title;
    private IssueState state;
    private boolean locked;
    @SerializedName("comments") private int commentCount;
    private String body;
    private User user;

    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;
    @SerializedName("closed_at") private Date closedAt;

    @SerializedName("body_html") private String bodyHtml;
    @SerializedName("repository_url") private String repositoryUrl;
    @SerializedName("html_url") private String htmlUrl;

    @SerializedName("closed_by") private User closedBy;

    private ArrayList<Label> labels;
    private User assignee;
    private ArrayList<User> assignees;
    private Milestone milestone;

    protected Issue(Parcel in) {
        id = in.readString();
        number = in.readInt();
        title = in.readString();
        int tmpState = in.readInt();
        state = tmpState == -1 ? null : IssueState.values()[tmpState];
        locked = in.readByte() != 0;
        commentCount = in.readInt();
        body = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        long tmpClosedAt = in.readLong();
        this.closedAt = tmpClosedAt == -1 ? null : new Date(tmpClosedAt);
        bodyHtml = in.readString();
        repositoryUrl = in.readString();
        htmlUrl = in.readString();
        closedBy = in.readParcelable(User.class.getClassLoader());
        labels = in.createTypedArrayList(Label.CREATOR);
        assignee = in.readParcelable(User.class.getClassLoader());
        assignees = in.createTypedArrayList(User.CREATOR);
        milestone = in.readParcelable(Milestone.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(number);
        dest.writeString(title);
        dest.writeInt(state == null ? -1 : state.ordinal());
        dest.writeByte((byte) (locked ? 1 : 0));
        dest.writeInt(commentCount);
        dest.writeString(body);
        dest.writeParcelable(user, flags);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeLong(this.closedAt != null ? this.closedAt.getTime() : -1);
        dest.writeString(bodyHtml);
        dest.writeString(repositoryUrl);
        dest.writeString(htmlUrl);
        dest.writeParcelable(closedBy, flags);
        dest.writeTypedList(labels);
        dest.writeParcelable(assignee, flags);
        dest.writeTypedList(assignees);
        dest.writeParcelable(milestone, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel in) {
            return new Issue(in);
        }

        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };
}
