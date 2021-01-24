package com.androiddvlpr.chatapp;

public class Model_User {

    private String username, name, uid, email;
    private Long lastLogin;

    public Model_User() {
    }

    public Model_User(String username, String name, String uid, String email, Long lastLogin) {
        this.username = username;
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.lastLogin = lastLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }
}
