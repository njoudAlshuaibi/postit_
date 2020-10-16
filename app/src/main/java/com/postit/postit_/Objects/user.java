package com.postit.postit_.Objects;

import android.widget.EditText;

public class user {
    public String username,email,password,college,major;

    public user(){}

    public user(String userName, String email, String signupPass,String scollege, String major){
        this.username = userName;
                this.email = email;
                this.password = signupPass;
                this.college = scollege;
                this.major = major;
    }
// test

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
}
