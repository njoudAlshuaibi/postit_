package com.postit.postit_.Admin;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.requests;
import com.postit.postit_.R;

import java.util.ArrayList;

public class requestadmin extends AppCompatActivity {
    private ListView requestsListView;
    private FirebaseDatabase database;
    private DatabaseReference requestsRef;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestadmin);

        Toolbar tb = findViewById(R.id.toolbar_request);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        requestsListView = findViewById(R.id.viewReq);
        final ArrayList<String> requestsList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitem, requestsList);
        requestsListView.setAdapter(adapter);
        ref = FirebaseDatabase.getInstance().getReference();
        requestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        requestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotr) {
                requestsList.clear();
                for (DataSnapshot snapshotx : snapshotr.getChildren()) {
                    requests reqObj = snapshotx.getValue(requests.class);
                    String reqdisplay = "Major: " + reqObj.getRequestedMajor() + "\nCourse: " + reqObj.getRequestedCourse() + "\nChapter/s: " + reqObj.getRequestedChapter() + "\nID: " + reqObj.getId();
                    requestsList.add(reqdisplay);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int whichItem = position;
                String hh = parent.getItemAtPosition(position).toString();
                final String requestKey = hh.substring(hh.indexOf("ID:") + 3, hh.length());
                new AlertDialog.Builder(requestadmin.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this request")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestsRef.child(requestKey.trim()).removeValue();
                                requestsList.remove(whichItem);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                return true;
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
            builder.setMessage("Are you sure you want to logout?");
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

        } else if (id == R.id.home) {
            startActivity(new Intent(requestadmin.this, mainAdmin.class));
        }
        return true;
    }
}