package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * https://developer.github.com/v3/issues/events/
 * @author biginsect
 * @date 2018/9/21.
 */


public class IssueEvent implements Parcelable{

    public enum Type{
        /**
         * The issue is closed by actor.
         * */
        closed,
        /**
         * The issue is reopened by actor.
         * */
        reopened,
        commented,
        @SerializedName("comment_deleted") commentDeleted,
        /**
         * The issue title is changed
         * */
        renamed,
        /**
         * The issue is locked or unlocked by actor
         * */
        locked,
        unlocked,
        /**
         * The issue is referenced by another issue.
         * */
        @SerializedName("cross-referenced") crossReferenced,

        assigned,
        unassigned,

        labeled,
        unlabeled,
        /**
         * The issue is added to a milestone
         * */
        milestoned,
        /**
         * This issue is removed from a milestone
         * */
        demilestoned
    }

    private String id;
    private User user;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;
    private String body;
    @SerializedName("body_html") private String bodyHtml;
    @SerializedName("event") private Type type;
    @SerializedName("html_url") private String htmlUrl;

    private User assignee;
    private User assigner;
    private User actor;
    private Label label;
    private Milestone milestone;
//    private Reactions reactions;
//    private IssueCrossReferencedSource source;

    private Issue parentIssue;

    protected IssueEvent(Parcel in) {
        id = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        body = in.readString();
        bodyHtml = in.readString();
        int tmpType = in.readInt();
        type = tmpType == -1 ? null : Type.values()[tmpType];
        htmlUrl = in.readString();
        assignee = in.readParcelable(User.class.getClassLoader());
        assigner = in.readParcelable(User.class.getClassLoader());
        actor = in.readParcelable(User.class.getClassLoader());
        label = in.readParcelable(Label.class.getClassLoader());
        milestone = in.readParcelable(Milestone.class.getClassLoader());
        parentIssue = in.readParcelable(Issue.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(user, flags);
        dest.writeLong(createdAt == null ? -1 : createdAt.getTime());
        dest.writeLong(updatedAt == null ? -1 : updatedAt.getTime());
        dest.writeString(body);
        dest.writeString(bodyHtml);
        dest.writeInt(type == null ? -1 : type.ordinal());
        dest.writeString(htmlUrl);
        dest.writeParcelable(assignee, flags);
        dest.writeParcelable(assigner, flags);
        dest.writeParcelable(actor, flags);
        dest.writeParcelable(label, flags);
        dest.writeParcelable(milestone, flags);
        dest.writeParcelable(parentIssue, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IssueEvent> CREATOR = new Creator<IssueEvent>() {
        @Override
        public IssueEvent createFromParcel(Parcel in) {
            return new IssueEvent(in);
        }

        @Override
        public IssueEvent[] newArray(int size) {
            return new IssueEvent[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getAssigner() {
        return assigner;
    }

    public void setAssigner(User assigner) {
        this.assigner = assigner;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Issue getParentIssue() {
        return parentIssue;
    }

    public void setParentIssue(Issue parentIssue) {
        this.parentIssue = parentIssue;
    }
}
