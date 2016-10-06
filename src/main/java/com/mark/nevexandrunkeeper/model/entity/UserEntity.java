package com.mark.nevexandrunkeeper.model.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/11/2016.
 */
@Entity
public class UserEntity implements Serializable {
    @Id
    private Long userId;
    private String name;
    private String location;
    private String gender;
    private String birthday;
    private Date signUpDate;
    private String athleteType;

    public UserEntity() {
        this.signUpDate = new Date();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getAthleteType() {
        return athleteType;
    }

    public void setAthleteType(String athleteType) {
        this.athleteType = athleteType;
    }
}
