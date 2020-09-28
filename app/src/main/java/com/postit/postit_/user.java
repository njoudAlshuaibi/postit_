package com.postit.postit_;

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
}
