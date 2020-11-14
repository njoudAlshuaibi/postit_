package com.postit.postit_.Student_Visitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.user;
import com.postit.postit_.R;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout9;
    NavigationView navigationView9;
    Toolbar toolbar3;
    TextInputEditText profile_email, profile_name,profile_major;
    private FirebaseUser Cuser;
    private user usera;
    private String userEmail;
    private String major;
    private String username;
    private Spinner spinnereditmajor;
    private Button saveChangesBtn;
    private TextView welcome;
    private FirebaseUser user;
    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout9 = findViewById(R.id.drawer_layout9);
        navigationView9 = findViewById(R.id.nav_view22);
        toolbar3 = findViewById(R.id.toolbar_profile);
        saveChangesBtn = findViewById(R.id.saveChangesBtn);
        setSupportActionBar(toolbar3);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("PROFILE");
        toolbar3.setTitleTextColor(0xFFB8B8B8);
        navigationView9.bringToFront();
        navigationView9.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout9,toolbar3,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout9.addDrawerListener(toggle);
        toggle.syncState();

        navigationView9.setNavigationItemSelectedListener(this);


        navigationView9.setNavigationItemSelectedListener(this);
        Menu menu= navigationView9.getMenu();

        menu.findItem(R.id.nav_login).setVisible(false);

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

            public void updateProfile(View view){

                if(isNameChanged() ||isPasswordChanged() || isEmailChanged() || isMajorChanged()){

                    Toast.makeText(this , "Data has been updated" , Toast.LENGTH_LONG).show();
            }
    }

    private boolean isMajorChanged() {
        return false;
    }

    private boolean isEmailChanged() {
        return false;
    }

    private boolean isNameChanged() {
        return false;
    }
    private boolean isPasswordChanged() {
       return false;
    }
}
