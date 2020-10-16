package com.postit.postit_.Admin;

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

import com.google.firebase.auth.FirebaseAuth;
import com.postit.postit_.MainActivity;
import com.postit.postit_.R;

public class mainAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        Toolbar toolb = findViewById(R.id.toolbar_Admin2);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        CardView meme = (CardView) findViewById(R.id.Manage);
        meme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (mainAdmin.this, AdminActivity.class));
            }
        });

        CardView njnj = (CardView) findViewById(R.id.AdminRequest);
        njnj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (mainAdmin.this, requestadmin.class));
            }
        });


        CardView juju = (CardView) findViewById(R.id.AdminBrowseNotes);
        juju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (mainAdmin.this, PopUpWindowAdmin.class));
            }
        });

    }

    private void openpopUpWindowadmin () {
        Intent popupwindowadmin = new Intent(mainAdmin.this,PopUpWindowAdmin.class);
        startActivity(popupwindowadmin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mainAdmin.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(mainAdmin.this, MainActivity.class));
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
            startActivity(new Intent(mainAdmin.this,mainAdmin.class));
        }
        return true;



    }
}