package com.mark.nevexandrunkeeper.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/12/2016.
 */
public class AdminCommentJobRunEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Date dateRan;
    private int timeTakenMs;
    private int commentsAdded;
    private int activeUsers;
    private int commentsFailed;
    private int commentsIgnored;
    private String commentUsed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateRan() {
        return dateRan;
    }

    public void setDateRan(Date dateRan) {
        this.dateRan = dateRan;
    }

    public int getTimeTakenMs() {
        return timeTakenMs;
    }

    public void setTimeTakenMs(int timeTakenMs) {
        this.timeTakenMs = timeTakenMs;
    }

    public int getCommentsAdded() {
        return commentsAdded;
    }

    public void setCommentsAdded(int commentsAdded) {
        this.commentsAdded = commentsAdded;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public int getCommentsFailed() {
        return commentsFailed;
    }

    public void setCommentsFailed(int commentsFailed) {
        this.commentsFailed = commentsFailed;
    }

    public int getCommentsIgnored() {
        return commentsIgnored;
    }

    public void setCommentsIgnored(int commentsIgnored) {
        this.commentsIgnored = commentsIgnored;
    }

    public String getCommentUsed() {
        return commentUsed;
    }

    public void setCommentUsed(String commentUsed) {
        this.commentUsed = commentUsed;
    }

    @Override
    public String toString() {
        return "AdminCommentJobRunEntity{" +
                "id=" + id +
                ", dateRan=" + dateRan +
                ", timeTakenMs=" + timeTakenMs +
                ", commentsAdded=" + commentsAdded +
                ", activeUsers=" + activeUsers +
                ", commentsFailed=" + commentsFailed +
                ", commentsIgnored=" + commentsIgnored +
                ", commentUsed='" + commentUsed + '\'' +
                '}';
    }
}
