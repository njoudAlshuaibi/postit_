package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class chapters extends AppCompatActivity {
    private FloatingActionButton fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        Toolbar toolbar2 = findViewById(R.id.toolbar_chapters);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpopupwindowchapters();
            }
        });
    }

    private void openpopupwindowchapters() {
        Intent popupwindow5 = new Intent(chapters.this,popupwindowchapters.class);
        startActivity(popupwindow5);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(chapters.this);
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(chapters.this, MainActivity.class));
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
            startActivity(new Intent(chapters.this,StudentActivity.class));
        }
        return true;

    }

}