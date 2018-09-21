package com.biginsect.easyhub.bean;

import android.os.Parcelable;

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
    private Issue
}
