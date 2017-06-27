package com.mark.nevexandrunkeeper.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * Created by NeVeX on 7/4/2016.
 */
@Entity
@Table(schema = "nevex", name = "oauth_users")
public class OAuthUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "code")
    private String oauthCode;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "updated_date")
    private Timestamp updatedDate;
    @Column(name = "redirect_url")
    private String redirectUrl;
    @Column(name = "state")
    private String state;
    @Column(name = "client_id")
    private String oauthClientId;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "is_friend_request_sent")
    private boolean isFriendRequestSent;
    @Column(name = "is_friend")
    private boolean isFriend;
    @Column(name = "is_active")
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

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

    public String getOauthCode() {
        return oauthCode;
    }

    public void setOauthCode(String oauthCode) {
        this.oauthCode = oauthCode;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOauthClientId() {
        return oauthClientId;
    }

    public void setOauthClientId(String oauthClientId) {
        this.oauthClientId = oauthClientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isFriendRequestSent() {
        return isFriendRequestSent;
    }

    public void setFriendRequestSent(boolean friendRequestSent) {
        isFriendRequestSent = friendRequestSent;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    @Override
    public String toString() {
        return "OAuthUserEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", oauthCode='" + oauthCode + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", state='" + state + '\'' +
                ", oauthClientId='" + oauthClientId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", isFriendRequestSent=" + isFriendRequestSent +
                ", isFriend=" + isFriend +
                ", isActive=" + isActive +
                '}';
    }
}
