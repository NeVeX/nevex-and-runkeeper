package com.mark.nevexandrunkeeper.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/12/2016.
 */
public class CommentJobEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private Date lastSuccessfulRun;
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
        return "CommentJobEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", lastSuccessfulRun=" + lastSuccessfulRun +
                ", lastFitnessId=" + lastFitnessId +
                '}';
    }
}
