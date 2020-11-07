package com.postit.postit_.Student_Visitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Objects.user;
import com.postit.postit_.R;

public class Profile extends AppCompatActivity {
    TextInputEditText profile_email, profile_name,profile_major;
    private FirebaseUser Cuser;
    private user usera;
    private String userEmail;
    private String major;
    private String username;
    private Spinner spinnereditmajor;

    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_email = findViewById(R.id.profile_email);
        profile_name = findViewById(R.id.profile_name);
        profile_major=findViewById(R.id.profile_major);
        spinnereditmajor = findViewById(R.id.spinnereditmajor);
        spinnereditmajor.setVisibility(View.INVISIBLE);


        Cuser = FirebaseAuth.getInstance().getCurrentUser();
        if (Cuser != null) {
            g.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        com.postit.postit_.Objects.user us = postSnapshot.getValue(user.class);
                        if(us.getEmail().equals(Cuser.getEmail())){
                            userEmail = us.getEmail();
                            username = us.getUsername();
                            major = us.getMajor();

                            profile_name.setText(username);
                            profile_email.setText(userEmail);
                            profile_major.setText(major);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });



        }

    }

}