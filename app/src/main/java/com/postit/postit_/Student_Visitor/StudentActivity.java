package com.postit.postit_.Student_Visitor;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.postit.postit_.MainActivity;
import com.postit.postit_.R;
import com.postit.postit_.helper.Session;


public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //m
DrawerLayout drawerLayout;
NavigationView navigationView;
Toolbar toolbar;
    Session session;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        //

       drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
//
         setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("HOME");
        toolbar.setTitleTextColor(0xFFB8B8B8);
        ///
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        session = new Session(getApplicationContext());
        Menu menu= navigationView.getMenu();
        if(user==null){
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_chat).setVisible(false);
            menu.findItem(R.id.nav_notification).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
        }
        else{
            menu.findItem(R.id.nav_login).setVisible(false);
        }
        CardView cv = (CardView) findViewById(R.id.Request);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null)
                {
                    new AlertDialog.Builder(StudentActivity.this)
                            .setTitle("you need to login first")
                            .setMessage("Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent (StudentActivity.this, MainActivity.class));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();                }
                else
                {
                    startActivity(new Intent (StudentActivity.this, Pop.class));
                }


            }
        });

        CardView ra = (CardView) findViewById(R.id.FavoriteList);
        ra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null)
                {
                    new AlertDialog.Builder(StudentActivity.this)
                            .setTitle("you need to login first")
                            .setMessage("Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent (StudentActivity.this,MainActivity.class));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();                }
                else
                {
                    startActivity(new Intent (StudentActivity.this, favoritelist.class));
                }

            }
        });

        CardView nj = (CardView) findViewById(R.id.Mynote);
        nj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null)
                {
                    new AlertDialog.Builder(StudentActivity.this)
                            .setTitle("you need to login first")
                            .setMessage("Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent (StudentActivity.this,MainActivity.class));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();                }
                else
                {
                    startActivity(new Intent (StudentActivity.this, mynotes.class));
                }

            }
        });

        CardView ma = (CardView) findViewById(R.id.BrowseNotes);
        ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpopUpWindow();
            }
        });



    }
@Override
public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

        drawerLayout.closeDrawer(GravityCompat.START);
    }
    else{
        super.onBackPressed();
    }
}

    private void openpopUpWindow() {
        Intent popupwindow2 = new Intent(StudentActivity.this, popUpWindow.class);
        startActivity(popupwindow2);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        if(user == null){
//            getMenuInflater().inflate(R.menu.visitor_menu, menu);}

     // else{
      // getMenuInflater().inflate(R.menu.menudrawer, menu);}
       return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1= new Intent(StudentActivity.this,StudentActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2= new Intent(StudentActivity.this,Profile.class);
                startActivity(intent2);
                break;
            case R.id.nav_chat:
                Intent intent3= new Intent(StudentActivity.this,chatActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_notification:
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);

                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                    // signOut
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(StudentActivity.this, MainActivity.class));

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
                break;
            case R.id.nav_login:
                Intent intent4= new Intent(StudentActivity.this,MainActivity.class);
                startActivity(intent4);

         }
        return true;}
}