package com.mark.nevexandrunkeeper.runkeeper.comment.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Entity
@Table(schema = "nevex", name = "comment_jobs")
public class CommentJobEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date_ran")
    private Timestamp dateRan;
    @Column(name = "time_taken_ms")
    private int timeTakenMs;
    @Column(name = "comments_added")
    private int commentsAdded;
    @Column(name = "active_users")
    private int activeUsers;
    @Column(name = "comments_failed")
    private int commentsFailed;
    @Column(name = "comments_ignored")
    private int commentsIgnored;
    @Column(name = "comment_used")
    private String commentUsed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateRan() {
        return dateRan;
    }

    public void setDateRan(Timestamp timestamp) {
        this.dateRan = timestamp;
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
