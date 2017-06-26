package com.mark.nevexandrunkeeper.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NeVeX on 7/4/2016.
 */
public class OAuthUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String oauthCode;
    private Date createdDate;
    private Date updatedDate;
    private String redirectUrl;
    private String state;
    private String oauthClientId;
    private String accessToken;
    private boolean isFriendRequestSent;
    private boolean isFriend;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
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
