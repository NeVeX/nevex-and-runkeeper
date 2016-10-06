package com.mark.nevexandrunkeeper.model.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Entity
public class AdminCommentJobRunEntity {
    @Id
    private Long id;
    private Date dateRan;
    private Long timeTakenMs;
    private Long commentsAdded;
    private Long activeUsers;
    private Long commentsFailed;
    private Long commentsIgnored;
    private String commentUsed;

    public String getCommentUsed() {
        return commentUsed;
    }

    public void setCommentUsed(String commentUsed) {
        this.commentUsed = commentUsed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRan() {
        return dateRan;
    }

    public void setDateRan(Date dateRan) {
        this.dateRan = dateRan;
    }

    public Long getTimeTakenMs() {
        return timeTakenMs;
    }

    public void setTimeTakenMs(Long timeTakenMs) {
        this.timeTakenMs = timeTakenMs;
    }

    public Long getCommentsAdded() {
        return commentsAdded;
    }

    public void setCommentsAdded(Long commentsAdded) {
        this.commentsAdded = commentsAdded;
    }

    public Long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Long getCommentsFailed() {
        return commentsFailed;
    }

    public void setCommentsFailed(Long commentsFailed) {
        this.commentsFailed = commentsFailed;
    }

    public Long getCommentsIgnored() {
        return commentsIgnored;
    }

    public void setCommentsIgnored(Long commentsIgnored) {
        this.commentsIgnored = commentsIgnored;
    }

    @Override
    public String toString() {
        return "AdminCommentJobRunEntity{" +
                "id=" + id +
                ", dateRan=" + dateRan +
                ", timeTakenMs=" + timeTakenMs +
                ", commentsAdded=" + commentsAdded +
                ", commentUsed=" + commentUsed +
                ", activeUsers=" + activeUsers +
                ", commentsFailed=" + commentsFailed +
                ", commentsIgnored=" + commentsIgnored +
                '}';
    }
}
