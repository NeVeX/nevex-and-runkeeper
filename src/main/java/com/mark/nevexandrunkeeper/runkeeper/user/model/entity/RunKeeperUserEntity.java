package com.mark.nevexandrunkeeper.runkeeper.user.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by NeVeX on 7/11/2016.
 */
@Entity
@Table(schema = "nevex", name = "runkeeper_users")
public class RunKeeperUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "updated_date")
    private Timestamp updatedDate;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "gender")
    private String gender;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "sign_up_date")
    private Date signUpDate;
    @Column(name = "athlete_type")
    private String athleteType;
    @Column(name = "is_friend_request_sent")
    private boolean isFriendRequestSent;
    @Column(name = "is_friend")
    private boolean isFriend;
    @Column(name = "is_active")
    private boolean isActive;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isFriendRequestSent() {
        return isFriendRequestSent;
    }

    public void setIsFriendRequestSent(boolean friendRequestSent) {
        isFriendRequestSent = friendRequestSent;
    }

    public boolean getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean friend) {
        isFriend = friend;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "RunKeeperUserEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", isActive=" + isActive +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", signUpDate=" + signUpDate +
                ", isFriendRequestSent=" + isFriendRequestSent +
                ", IsFriend=" + isFriend +
                ", athleteType='" + athleteType + '\'' +
                '}';
    }
}
