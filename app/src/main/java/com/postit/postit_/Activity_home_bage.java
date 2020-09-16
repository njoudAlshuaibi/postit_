package com.postit.postit_;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_home_bage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_bage);
        Toolbar toolbar = findViewById(R.id.toolbar_homepage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.exit){
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_home_bage.this);
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  // FirebaseAuth.getInstance().signOut();
//                    startActivity(new intent(Activity_home_bage.this, MainActivity.class));
                    //

//private Button logout;
//
//logout = (Button)FindviewById(R.id.signout);
//
//logout.setOnClickListener(new View.OnClickListener() {
//
//        public void onClick(View view) {
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new intent(profile.this, MainActivity.class));
//        }//End onClick()
//    });
                    finish();
                    Intent intent = new Intent(Activity_home_bage.this,MainActivity.class);
                    startActivity(intent);

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
        return true;
    }
}