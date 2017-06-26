package com.mark.nevexandrunkeeper.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/11/2016.
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private String name;
    private String location;
    private char gender;
    private String birthday;
    private Date signUpDate;
    private String athleteType;

    public String getAthleteType() {
        return athleteType;
    }

    public void setAthleteType(String athleteType) {
        this.athleteType = athleteType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
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

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", signUpDate=" + signUpDate +
                ", athleteType='" + athleteType + '\'' +
                '}';
    }
}
