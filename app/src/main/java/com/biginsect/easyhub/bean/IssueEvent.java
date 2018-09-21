package com.biginsect.easyhub.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * https://developer.github.com/v3/issues/events/
 * @author lipeng-ds3
 * @date 2018/9/21.
 */


public class IssueEvent {

    public enum Type{
        closed,
        reopened,
        commented,
        @SerializedName("comment_deleted") commentDeleted,
        renamed,
        locked,
        unlocked,
        @SerializedName("cross-referenced") crossReferenced,
        assigned,
        unassigned,
        labeled,
        unlabeled,
        milestoned,
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
    private Reactions reactions;
    private IssueCrossReferencedSource source;

    private Issue parentIssue;
}
