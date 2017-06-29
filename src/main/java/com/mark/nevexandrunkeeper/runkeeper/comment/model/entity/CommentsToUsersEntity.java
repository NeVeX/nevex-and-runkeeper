package com.mark.nevexandrunkeeper.runkeeper.comment.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Entity
@Table(schema = "nevex", name = "comments_to_users")
public class CommentsToUsersEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "comment")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentsToUsersEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", comment=" + comment +
                '}';
    }
}
