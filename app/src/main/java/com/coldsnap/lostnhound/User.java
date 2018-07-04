package com.coldsnap.lostnhound;

public class User {

    String fullName;
    String email;
    String userId;


    public User(String fullName, String email, String userId) {
        this.fullName = fullName;
        this.email = email;
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
