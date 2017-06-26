package com.mark.nevexandrunkeeper.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/11/2016.
 */
@Entity
@Table(schema = "nevex", name = "RunKeeperUsers")
public class RunKeeperUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "UserId")
    private int userId;
    @Basic
    @Column(name = "Name")
    private String name;
    @Basic
    @Column(name = "Location")
    private String location;
    @Basic
    @Column(name = "Gender")
    private String gender;
    @Basic
    @Column(name = "Birthday")
    private String birthday;
    @Basic
    @Column(name = "SignUpDate")
    private Date signUpDate;
    @Basic
    @Column(name = "AthleteType")
    private String athleteType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "RunKeeperUserEntity{" +
                "id=" + id +
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
