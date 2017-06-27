package com.mark.nevexandrunkeeper.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Entity
@Table(schema = "nevex", name = "latest_comment_for_users")
public class LatestCommentForUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "last_comment_added_date")
    private Timestamp lastCommentAddedDate;
    @Column(name = "last_fitness_id")
    private Long lastFitnessId;

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

    public Timestamp getLastCommentAddedDate() {
        return lastCommentAddedDate;
    }

    public void setLastCommentAddedDate(Timestamp lastCommentAddedDate) {
        this.lastCommentAddedDate = lastCommentAddedDate;
    }

    public Long getLastFitnessId() {
        return lastFitnessId;
    }

    public void setLastFitnessId(Long lastFitnessId) {
        this.lastFitnessId = lastFitnessId;
    }

    @Override
    public String toString() {
        return "LatestCommentForUserEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", lastCommentAddedDate=" + lastCommentAddedDate +
                ", lastFitnessId=" + lastFitnessId +
                '}';
    }
}
