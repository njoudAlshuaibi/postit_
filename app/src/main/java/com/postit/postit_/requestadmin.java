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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class requestadmin extends AppCompatActivity {
    private ListView requestsListView;
    private FirebaseDatabase database;
    private DatabaseReference requestsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestadmin);

        Toolbar tb = findViewById(R.id.toolbar_request);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        requestsListView = findViewById(R.id.viewReq);
        final ArrayList<String> requestsList= new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.listitem, requestsList);
        requestsListView.setAdapter(adapter);
        requestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        requestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotr) {
                requestsList.clear();
                for (DataSnapshot snapshotx : snapshotr.getChildren()){
                    requests reqObj = snapshotx.getValue(requests.class);
                    String reqdisplay="Major: " +reqObj.getRequestedMajor()+"\nCourse: "+reqObj.getRequestedCourse()+"\nChapter/s: "+reqObj.getRequestedChapter();
                    requestsList.add(reqdisplay);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
            AlertDialog.Builder builder = new AlertDialog.Builder(requestadmin.this);
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(requestadmin.this, MainActivity.class));
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
            startActivity(new Intent(requestadmin.this,mainAdmin.class));
        }
        return true;
    }
}