package com.postit.postit_.Objects;

import android.widget.EditText;

public class user {
    public String username, email, password, college, major, id;

    public user() {
    }

    public user(String username, String email, String password, String college, String major, String id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.college = college;
        this.major = major;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
// test

}
