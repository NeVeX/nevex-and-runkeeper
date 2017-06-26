package com.mark.nevexandrunkeeper.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Entity
@Table(schema = "nevex", name = "LatestCommentForUsers")
public class LatestCommentForUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "UserId")
    private int userId;
    @Basic
    @Column(name = "LastCommentAdded")
    private Date lastSuccessfulRun;
    @Basic
    @Column(name = "LastFitnessId")
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

    public Date getLastSuccessfulRun() {
        return lastSuccessfulRun;
    }

    public void setLastSuccessfulRun(Date lastSuccessfulRun) {
        this.lastSuccessfulRun = lastSuccessfulRun;
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
                ", lastSuccessfulRun=" + lastSuccessfulRun +
                ", lastFitnessId=" + lastFitnessId +
                '}';
    }
}
