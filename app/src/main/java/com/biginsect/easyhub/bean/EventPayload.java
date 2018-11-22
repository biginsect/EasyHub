package com.biginsect.easyhub.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.biginsect.easyhub.util.StringUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * https://developer.github.com/v3/activity/events/types/
 * @author biginsect
 * @date 2018/9/21.
 */


public class EventPayload implements Parcelable{

    public enum TypeRef {
        repository, branch, tag
    }

    public enum TypeIssueEventAction {
        assigned, unassigned, labeled, unlabeled, opened,
        edited, milestoned, demilestoned, closed, reopened
    }

    public enum TypeMemberEventAction {
        added, deleted, edited
    }

    public enum TypeOrgBlockEventAction {
        blocked, unblocked
    }

    public enum TypePullRequestReviewCommentEventAction {
        created, edited, deleted
    }

    public enum TypePullRequestReviewEventAction {
        submitted, edited, dismissed
    }

    /**PushEvent & CreateEvent*/
    private String ref;
    /**PushEvent*/
    @SerializedName("push_id") private String pushId;
    private int size;
    @SerializedName("distinct_size") private int distinctSize;
    private String head;
    private String before;
    private ArrayList<PushEventCommit> commits;

    /**WatchEvent & PullRequestEvent*/
    private String action;

    /**CreateEvent*/
    @SerializedName("pusher_type") private TypeRef typeRef;
    @SerializedName("master_branch") private String masterBranch;
    @SerializedName("ref_type") private String pusherType;
    private String description;

    /**ReleaseEvent*/
    private Release release;

    /**IssueCommentEvent*/
    private Issue issue;
    private IssueEvent comment;

    /**MemberEvent*/
    private User member;
    private User organization;

    @SerializedName("blocked_user") private User blockedUser;

    public EventPayload(){

    }

    protected EventPayload(Parcel in) {
        ref = in.readString();
        pushId = in.readString();
        size = in.readInt();
        distinctSize = in.readInt();
        head = in.readString();
        before = in.readString();
        commits = in.createTypedArrayList(PushEventCommit.CREATOR);
        action = in.readString();
        int tmpTypeRef = in.readInt();
        typeRef = tmpTypeRef == -1 ? null : TypeRef.values()[tmpTypeRef];
        masterBranch = in.readString();
        pusherType = in.readString();
        description = in.readString();
        release = in.readParcelable(Release.class.getClassLoader());
        issue = in.readParcelable(Issue.class.getClassLoader());
        comment = in.readParcelable(IssueEvent.class.getClassLoader());
        member = in.readParcelable(User.class.getClassLoader());
        organization = in.readParcelable(User.class.getClassLoader());
        blockedUser = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ref);
        dest.writeString(pushId);
        dest.writeInt(size);
        dest.writeInt(distinctSize);
        dest.writeString(head);
        dest.writeString(before);
        dest.writeTypedList(commits);
        dest.writeString(action);
        dest.writeInt(typeRef == null ? -1 : typeRef.ordinal());
        dest.writeString(masterBranch);
        dest.writeString(pusherType);
        dest.writeString(description);
        dest.writeParcelable(release, flags);
        dest.writeParcelable(issue, flags);
        dest.writeParcelable(comment, flags);
        dest.writeParcelable(member, flags);
        dest.writeParcelable(organization, flags);
        dest.writeParcelable(blockedUser, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EventPayload> CREATOR = new Creator<EventPayload>() {
        @Override
        public EventPayload createFromParcel(Parcel in) {
            return new EventPayload(in);
        }

        @Override
        public EventPayload[] newArray(int size) {
            return new EventPayload[size];
        }
    };

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDistinctSize() {
        return distinctSize;
    }

    public void setDistinctSize(int distinctSize) {
        this.distinctSize = distinctSize;
    }

    public String getBranch(){
        return StringUtils.INSTANCE.isBlankString(ref) ? null : ref.substring(ref.lastIndexOf("/") + 1);
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public ArrayList<PushEventCommit> getCommits() {
        return commits;
    }

    public void setCommits(ArrayList<PushEventCommit> commits) {
        this.commits = commits;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public TypeRef getTypeRef() {
        return typeRef;
    }

    public void setTypeRef(TypeRef typeRef) {
        this.typeRef = typeRef;
    }

    public String getMasterBranch() {
        return masterBranch;
    }

    public void setMasterBranch(String masterBranch) {
        this.masterBranch = masterBranch;
    }

    public String getPusherType() {
        return pusherType;
    }

    public void setPusherType(String pusherType) {
        this.pusherType = pusherType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public IssueEvent getComment() {
        return comment;
    }

    public void setComment(IssueEvent comment) {
        this.comment = comment;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public User getOrganization() {
        return organization;
    }

    public void setOrganization(User organization) {
        this.organization = organization;
    }

    public User getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(User blockedUser) {
        this.blockedUser = blockedUser;
    }
}
