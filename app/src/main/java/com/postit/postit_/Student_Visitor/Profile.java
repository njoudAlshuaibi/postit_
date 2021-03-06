package com.postit.postit_.Student_Visitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.major;
import com.postit.postit_.Objects.user;
import com.postit.postit_.R;

import java.util.ArrayList;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout9;
    NavigationView navigationView9;
    Toolbar toolbar3;
    EditText  profile_name,profile_email;
    private FirebaseUser Cuser;
    private user usera;
    private String userEmail;
    private String major;
    private String username;
    private String userId;
    private String password;
    private String college, major3;
    private Spinner spinnereditmajor;
    private Button saveChangesBtn, editPRO, cancel14;
    private TextView welcome, namefromDB, emailfromDB,profile_major;
    private FirebaseUser user;
    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");
    private DatabaseReference majorRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout9 = findViewById(R.id.drawer_layout9);
        navigationView9 = findViewById(R.id.nav_view22);
        toolbar3 = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar3);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("PROFILE");
        toolbar3.setTitleTextColor(0xFF000000);
        navigationView9.bringToFront();
        navigationView9.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout9,toolbar3,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout9.addDrawerListener(toggle);
        toggle.syncState();

        navigationView9.setNavigationItemSelectedListener(this);
        navigationView9.setNavigationItemSelectedListener(this);
        Menu menu= navigationView9.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);


        editPRO= findViewById(R.id.editPRO);
        cancel14 = findViewById(R.id.cancel14);
        saveChangesBtn = findViewById(R.id.saveChangesBtn);
        cancel14.setVisibility(View.INVISIBLE);
        saveChangesBtn.setVisibility(View.INVISIBLE);



        profile_email = findViewById(R.id.profile_email);
        profile_email.setVisibility(View.INVISIBLE);
        emailfromDB=findViewById(R.id.emailfromDB);

        namefromDB = findViewById(R.id.namefromDB);
        profile_name = findViewById(R.id.profile_name);
        profile_name.setVisibility(View.INVISIBLE);



        majorRef = FirebaseDatabase.getInstance().getReference().child("Majors");
        profile_major = findViewById(R.id.profile_major);
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
                            userId = us.getId();
                            college = us.getCollege();
                            password = us.getPassword();
                            namefromDB.setText(username);
                            emailfromDB.setText(userEmail);
                            profile_major.setText(major);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });



        }



        final ArrayList<String> majorList = new ArrayList<>();
        final ArrayAdapter adapter1 = new ArrayAdapter<String>(this, R.layout.listitem, majorList);
        spinnereditmajor.setAdapter(adapter1);
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                majorList.clear();
                majorList.add(major);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    com.postit.postit_.Objects.major majorObj = postSnapshot.getValue(com.postit.postit_.Objects.major.class);
                    if(!major.equals(majorObj.getMajorName()))
                         majorList.add(majorObj.getMajorName());
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        spinnereditmajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                major3 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        editPRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel14.setVisibility(View.VISIBLE);
                saveChangesBtn.setVisibility(View.VISIBLE);

                profile_name.setVisibility(View.VISIBLE);
                profile_name.setText(username);
                namefromDB.setVisibility(View.INVISIBLE);

                profile_email.setVisibility(View.VISIBLE);
                profile_email.setText(userEmail);
                emailfromDB.setVisibility(View.INVISIBLE);


                profile_major.setVisibility(View.INVISIBLE);

                spinnereditmajor.setVisibility(View.VISIBLE);


            }
        });
        cancel14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel14.setVisibility(View.INVISIBLE);
                saveChangesBtn.setVisibility(View.INVISIBLE);

                profile_name.setVisibility(View.INVISIBLE);
                namefromDB.setVisibility(View.VISIBLE);

                profile_email.setVisibility(View.INVISIBLE);
                emailfromDB.setVisibility(View.VISIBLE);


                profile_major.setVisibility(View.VISIBLE);
                spinnereditmajor.setVisibility(View.INVISIBLE);


            }
        });





        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo();
                cancel14.setVisibility(View.INVISIBLE);
                saveChangesBtn.setVisibility(View.INVISIBLE);

                profile_name.setVisibility(View.INVISIBLE);
                namefromDB.setVisibility(View.VISIBLE);

                profile_email.setVisibility(View.INVISIBLE);
                emailfromDB.setVisibility(View.VISIBLE);


                profile_major.setVisibility(View.VISIBLE);
                spinnereditmajor.setVisibility(View.INVISIBLE);
            }
        });


    }

//    public void onClick(View v){
//        switch (v.getId()){
//            case R.id.saveChangesBtn:
//                updateInfo();
//                break;
//
//        }
//    }

    @Override
    public void onBackPressed(){
        if(drawerLayout9.isDrawerOpen(GravityCompat.START)){

            drawerLayout9.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        welcome= (TextView) findViewById(R.id.welcome1);
        if(Cuser!=null){
            g.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        com.postit.postit_.Objects.user us = postSnapshot.getValue(com.postit.postit_.Objects.user.class);
                        if(us.getEmail().equals(Cuser.getEmail())){
                            welcome.setText("Welcome"+" "+us.getUsername());

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });}
        else{
            welcome.setText("     "+"Welcome");
        }
        return true;
    }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent1= new Intent(Profile.this,StudentActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_profile:
                    Intent intent2= new Intent(Profile.this,Profile.class);
                    startActivity(intent2);
                    break;
                case R.id.nav_chat:
                    Intent intent3= new Intent(Profile.this,usersChats.class);
                    startActivity(intent3);
                    break;

                case R.id.nav_logout:

                    AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                    builder.setMessage("Are you sure you want to logout?");
                    builder.setCancelable(true);

                    builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                        // signOut
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(Profile.this, MainActivity.class));

                        }
                    });


                    builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


            }
            return true;}

//            public void updateProfile(View view){
//
//                if(isNameChanged() ||isPasswordChanged() || isEmailChanged() || isMajorChanged()){
//
//                    Toast.makeText(this , "Data has been updated" , Toast.LENGTH_LONG).show();
//            } else
//                    Toast.makeText(this , "Data is the same and can not be updated" , Toast.LENGTH_LONG).show();
//    }
//
//    private boolean isMajorChanged() {
//
//        if(!major.equals(profile_major.getText().toString())){
//            g.child(username).child("major").setValue(profile_major.getText().toString());
//
//            return true;
//
//        }else
//            return false;
//    }
//
//    private boolean isEmailChanged() {
//        return false;
//    }
//
//    private boolean isNameChanged() {
//
//        if(!username.equals(profile_name.getText().toString())){
//            g.child(username).child("username").setValue(profile_name.getText().toString());
//
//            return true;
//
//        }else
//        return false;
//    }
//
//    private boolean isPasswordChanged() {
//       return false;
//    }
//
    private void updateInfo() {

       // Cuser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef =  FirebaseDatabase.getInstance().getReference("users").child(userId);

        final String username= profile_name.getText().toString().trim();
        final String email= profile_email.getText().toString().trim();


        if(username.isEmpty()){
            profile_name.setError("User name is required");
            profile_name.requestFocus();

            return;
        }

        if(username.length()>20){
            profile_name.setError("Username can't be more than 20 character");
            profile_name.requestFocus();

            return;
        }
        if(username.length()<3){
            profile_name.setError("Username can't be less than 3 character");
            profile_name.requestFocus();

            return;
        }

        if(!username.matches("[a-zA-Z]+")){
            profile_name.setError("Username must contains letter");
            profile_name.requestFocus();

            return;
        }

        if(email.isEmpty()){
            profile_email.setError("Email is required");
            profile_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            profile_email.setError("Please provide valid email");
            profile_email.requestFocus();
            return;
        }

        user User = new user(username , email , password , college , major3 , userId);
        userRef.setValue(User);
        Toast.makeText(this , "Information has been updated successfully" , Toast.LENGTH_LONG).show();


    }



}
