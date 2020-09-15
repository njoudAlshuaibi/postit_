package com.postit.postit_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView createAccount;//test
    private TextView forgetPassword;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");


        createAccount = (TextView) findViewById(R.id.createAccount);
        forgetPassword  = (TextView) findViewById(R.id.forgetPassword);
        createAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
            }//End onClick()
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, forgetPasswordActivity.class));
            }
        });

    }



}






















