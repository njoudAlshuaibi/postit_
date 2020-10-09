package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Visitors_activity extends AppCompatActivity {

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitors_activity);
        Toolbar toolb = findViewById(R.id.toolbar_Vistor);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        //browse note card
        CardView nj = (CardView) findViewById(R.id.BrowseNotes2);
        nj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpopupwindowvisitors();
            }
        });

        //fav list card
        CardView favList = (CardView) findViewById(R.id.FavoriteList);
        favList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Visitors_activity.this)
                        .setTitle("you need to login first")
                        .setMessage("Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent (Visitors_activity.this,MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        //my notes card
        CardView myNotes = (CardView) findViewById(R.id.Mynote);
        myNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Visitors_activity.this)
                        .setTitle("you need to login first")
                        .setMessage("Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent (Visitors_activity.this,MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        //my notes card
        CardView request = (CardView) findViewById(R.id.Request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Visitors_activity.this)
                        .setTitle("you need to login first")
                        .setMessage("Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent (Visitors_activity.this,MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


    }

    private void openpopupwindowvisitors() {
        Intent popupwindow3 = new Intent(Visitors_activity.this,popupwindowvisitors.class);
        startActivity(popupwindow3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.visitor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Visitors_activity.this);
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Visitors_activity.this, MainActivity.class));
//                    finish();
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
        else if (id==R.id.home){
            if(currentUser != null){
            startActivity(new Intent(Visitors_activity.this,MainActivity.class));
        }else {
                new AlertDialog.Builder(Visitors_activity.this)
                        .setTitle("you need to login first")
                        .setMessage("Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent (Visitors_activity.this,MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        }
        return true;


    }
}
