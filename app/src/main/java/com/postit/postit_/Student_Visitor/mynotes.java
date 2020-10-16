package com.postit.postit_.Student_Visitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Adapter.BrowseNoteAdapter;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.note;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class mynotes extends AppCompatActivity {
    private FloatingActionButton fab;
    private note b;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    public static final String EXTRA_TEXTww = "com.postit.postit_.EXTRA_TEXT";
    public static final String EXTRA_TEXT2ee = "com.postit.postit_.EXTRA_TEXT2";
    public static final String EXTRA_TEXT2e = "com.postit.postit_.EXTRA_TEXT3";
    private RecyclerView titleList;
    String curUserEmail="";
    private DatabaseReference noteRef;
    List<note> noteList = new ArrayList<>();
    BrowseNoteAdapter noteAdapter ;
    RecyclerView recyclerView;
    private FirebaseUser user;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynotes);


        Toolbar toolba = findViewById(R.id.toolbar_mynotes);
        setSupportActionBar(toolba);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencreatnotepopup();
            }
        });
        userEmail = "";
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail().trim();
        } else {
            // No user is signed in
        }

        noteRef= FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef.keepSynced(true);

        noteAdapter = new BrowseNoteAdapter(this, noteList, new CustomItemClickListener() {
            @Override
            public void OnItemClick(View v, int pos) {

                note n = noteList.get(pos);
                String s = n.getDate();
                String a = n.getCaption();


            } // end on item click listener
        });
        recyclerView = findViewById(R.id.MynoteRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(noteAdapter);

        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    noteList.clear();
                    noteAdapter.notifyDataSetChanged();

                    for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                        note a = messageSnapshot.getValue(note.class);

                        if (a.getEmail().trim().equals(userEmail))
                            noteList.add(a);
                    }
                }
                else {
                    Log.d("===" , "No Data Was Found");
                }

                noteAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mynotes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mynotes.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(mynotes.this, MainActivity.class));

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
            startActivity(new Intent(mynotes.this, StudentActivity.class));
        }
        return true;
    }

    private void opencreatnotepopup() {
        Intent popupwindow4 = new Intent(mynotes.this, creatnotepopup.class);
        startActivity(popupwindow4);
    }

}