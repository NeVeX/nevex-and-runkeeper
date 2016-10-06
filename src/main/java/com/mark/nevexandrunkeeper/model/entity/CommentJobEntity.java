package com.mark.nevexandrunkeeper.model.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Entity
public class CommentJobEntity implements Serializable {
    @Id
    private Long id;
    @Index
    private Long userId;
    private Date lastSuccessfulRun;
    private Long lastFitnessId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLastFitnessId() {
        return lastFitnessId;
    }

    public void setLastFitnessId(Long lastFitnessId) {
        this.lastFitnessId = lastFitnessId;
    }

    public Date getLastSuccessfulRun() {
        return lastSuccessfulRun;
    }

    public void setLastSuccessfulRun(Date lastSuccessfulRun) {
        this.lastSuccessfulRun = lastSuccessfulRun;
    }
}
