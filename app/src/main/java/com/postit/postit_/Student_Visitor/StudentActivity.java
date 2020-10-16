package com.postit.postit_.Student_Visitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.postit.postit_.MainActivity;
import com.postit.postit_.R;
import com.postit.postit_.helper.Session;


public class StudentActivity extends AppCompatActivity {

    Session session;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = findViewById(R.id.toolbar_Student);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new Session(getApplicationContext());
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

    private void openpopUpWindow() {
        Intent popupwindow2 = new Intent(StudentActivity.this, popUpWindow.class);
        startActivity(popupwindow2);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(StudentActivity.this, MainActivity.class));
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

        }else if (id==R.id.home){
            startActivity(new Intent(StudentActivity.this,StudentActivity.class));
        }
        return true;

    }

}