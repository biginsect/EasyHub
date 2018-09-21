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

public class Event implements Parcelable{

    /**
     * 类型
     * */
    public enum EventType{
        CommitComment,
        Create,
        Delete,
        Fork,
        Gollum,
        Installation,
        InstallationRepositories,
        IssueComment,
        Issues,
        MarketplacePurchase,
        Member,
        OrgBlock,
        ProjectCard,
        ProjectColumn,
        Project,
        Public,
        PullRequest,
        PullRequestReview,
        PullRequestReviewComment,
        Push,
        Release,
        Watch,
        Deployment,
        DeploymentStatus,
        Membership,
        Milestone,
        Organization,
        PageBuild,
        Repository,
        Status,
        Team,
        TeamAdd,
        Label,

        Download,
        Follow,
        ForkApply,
        Gist
    }
    private String id;
    private User actor;
    private EventType type;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("public") private boolean isPublic;
    private EventPayload payload;
    private Repository repo;
    private User org;


    public Event(){

    }


    protected Event(Parcel in) {
        id = in.readString();
        int tmpType = in.readInt();
        type = tmpType == -1 ? null :EventType.values()[tmpType];
        actor = in.readParcelable(User.class.getClassLoader());
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1? null : new Date(tmpCreatedAt);
        isPublic = in.readByte() != 0;
        payload = in.readParcelable(EventPayload.class.getClassLoader());
        repo = in.readParcelable(Repository.class.getClassLoader());
        org = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(actor, flags);
        dest.writeInt(type == null ? -1 : this.type.ordinal());
        dest.writeLong(createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeByte((byte) (isPublic ? 1 : 0));
        dest.writeParcelable(payload, flags);
        dest.writeParcelable(repo, flags);
        dest.writeParcelable(org, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    /*
    {
    "type": "Event",
    "public": true,
    "payload": {
    },
    "repo": {
      "id": 3,
      "name": "octocat/Hello-World",
      "url": "https://api.github.com/repos/octocat/Hello-World"
    },
    "actor": {
      "id": 1,
      "login": "octocat",
      "gravatar_id": "",
      "avatar_url": "https://github.com/images/error/octocat_happy.gif",
      "url": "https://api.github.com/users/octocat"
    },
    "org": {
      "id": 1,
      "login": "github",
      "gravatar_id": "",
      "url": "https://api.github.com/orgs/github",
      "avatar_url": "https://github.com/images/error/octocat_happy.gif"
    },
    "created_at": "2011-09-06T17:26:27Z",
    "id": "12345"
  }
     */
}
