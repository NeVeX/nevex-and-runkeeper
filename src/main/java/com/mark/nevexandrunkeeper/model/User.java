package com.mark.nevexandrunkeeper.model;

import java.util.Objects;

/**
 * Created by NeVeX on 7/12/2016.
 */
public final class User {

    private final String name;
    private final int userId;
    private final boolean isActive;

    public User(int userId, String name, boolean isActive) {
        this.name = name;
        this.userId = userId;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
