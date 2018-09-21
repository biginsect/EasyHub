package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author lipeng-ds3
 * @date 2018/9/21.
 */


public class Milestone implements Parcelable{

    public enum MilestoneState {
        open, closed
    }

    private long id;
    private int number;
    private MilestoneState state;
    private String title;
    private String description;
    private User creator;

    @SerializedName("open_issues") private int openIssues;
    @SerializedName("closed_issues") private int closedIssues;

    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;
    @SerializedName("closed_at") private Date closedAt;
    @SerializedName("due_on") private Date dueOn;


    protected Milestone(Parcel in) {
        id = in.readLong();
        number = in.readInt();
        int tmpState = in.readInt();
        state = tmpState == -1 ? null : MilestoneState.values()[tmpState];
        title = in.readString();
        description = in.readString();
        creator = in.readParcelable(User.class.getClassLoader());
        openIssues = in.readInt();
        closedIssues = in.readInt();
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);

        long tmpUpdateAt = in.readLong();
        updatedAt = tmpUpdateAt == -1 ? null : new Date(tmpUpdateAt);

        long tmpClosedAt = in.readLong();
        closedAt = tmpClosedAt == -1 ? null : new Date(tmpClosedAt);

        long tmpDueOn = in.readLong();
        dueOn = tmpDueOn == -1 ? null : new Date(tmpDueOn);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(number);
        dest.writeInt(state == null ? -1 : state.ordinal());
        dest.writeString(title);
        dest.writeString(description);
        dest.writeParcelable(creator, flags);
        dest.writeInt(openIssues);
        dest.writeInt(closedIssues);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeLong(closedAt != null ? closedAt.getTime() : -1);
        dest.writeLong(dueOn != null ? dueOn.getTime() : -1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Milestone> CREATOR = new Creator<Milestone>() {
        @Override
        public Milestone createFromParcel(Parcel in) {
            return new Milestone(in);
        }

        @Override
        public Milestone[] newArray(int size) {
            return new Milestone[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public MilestoneState getState() {
        return state;
    }

    public void setState(MilestoneState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public int getClosedIssues() {
        return closedIssues;
    }

    public void setClosedIssues(int closedIssues) {
        this.closedIssues = closedIssues;
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

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public Date getDueOn() {
        return dueOn;
    }

    public void setDueOn(Date dueOn) {
        this.dueOn = dueOn;
    }
}

/*
 "milestone": {
    "url": "https://api.github.com/repos/octocat/Hello-World/milestones/1",
    "html_url": "https://github.com/octocat/Hello-World/milestones/v1.0",
    "labels_url": "https://api.github.com/repos/octocat/Hello-World/milestones/1/labels",
    "id": 1002604,
    "node_id": "MDk6TWlsZXN0b25lMTAwMjYwNA==",
    "number": 1,
    "state": "open",
    "title": "v1.0",
    "description": "Tracking milestone for version 1.0",
    "creator": {
      "login": "octocat",
      "id": 1,
      "node_id": "MDQ6VXNlcjE=",
      "avatar_url": "https://github.com/images/error/octocat_happy.gif",
      "gravatar_id": "",
      "url": "https://api.github.com/users/octocat",
      "html_url": "https://github.com/octocat",
      "followers_url": "https://api.github.com/users/octocat/followers",
      "following_url": "https://api.github.com/users/octocat/following{/other_user}",
      "gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
      "organizations_url": "https://api.github.com/users/octocat/orgs",
      "repos_url": "https://api.github.com/users/octocat/repos",
      "events_url": "https://api.github.com/users/octocat/events{/privacy}",
      "received_events_url": "https://api.github.com/users/octocat/received_events",
      "type": "User",
      "site_admin": false
    },
    "open_issues": 4,
    "closed_issues": 8,
    "created_at": "2011-04-10T20:09:31Z",
    "updated_at": "2014-03-03T18:58:10Z",
    "closed_at": "2013-02-12T13:22:01Z",
    "due_on": "2012-10-09T23:39:01Z"
  }
 */
