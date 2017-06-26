package com.mark.nevexandrunkeeper.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Entity
@Table(schema = "nevex", name = "CommentJobs")
public class CommentJobEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "DateRan")
    private Date dateRan;
    @Basic
    @Column(name = "TimeTakenMs")
    private int timeTakenMs;
    @Basic
    @Column(name = "CommentsAdded")
    private int commentsAdded;
    @Basic
    @Column(name = "ActiveUsers")
    private int activeUsers;
    @Basic
    @Column(name = "CommentsFailed")
    private int commentsFailed;
    @Basic
    @Column(name = "CommentsIgnored")
    private int commentsIgnored;
    @Basic
    @Column(name = "CommentUsed")
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
        return "CommentJobEntity{" +
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
