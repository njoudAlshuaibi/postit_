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
import android.widget.TextView;

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
    private DatabaseReference noteRef, notesRef;
    List<note> noteList = new ArrayList<>();
    BrowseNoteAdapter noteAdapter ;
    RecyclerView recyclerView;
    private FirebaseUser user;
    private String userEmail;
    private TextView textView;

    public static final String preTitel = "com.postit.postit_.preTitel";
    public static final String preCaption = "com.postit.postit_.preCaption";
    public static final String preEmail = "com.postit.postit_.preEmail";
    public static final String preID = "com.postit.postit_.preID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynotes);


        Toolbar toolba = findViewById(R.id.toolbar_mynotes);
        setSupportActionBar(toolba);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textView = (TextView) findViewById(R.id.tvnotes);
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
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef= FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef.keepSynced(true);

        noteAdapter = new BrowseNoteAdapter(this, noteList, new CustomItemClickListener() {
            @Override
            public void OnItemClick(View v, int pos) {

                note n = noteList.get(pos);
                String s = n.getTitle();
                String a = n.getCaption();
                String m = n.getEmail();
                String i = n.getId();

                Intent in = new Intent(mynotes.this, previewnote.class);
                in.putExtra(preTitel,""+s );
                in.putExtra(preCaption,""+a );
                in.putExtra(preEmail,""+m );
                in.putExtra(preID,i);
                startActivity(in);

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
        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotrf) {
                for (DataSnapshot childrf : snapshotrf.getChildren()) {
                    note findNote = childrf.getValue(note.class);
                    String EmailN = findNote.getEmail();
                    if (EmailN.equals(userEmail)) {
                        textView.setText("");
                        break;
                    }

                    else {
                        textView.setText("no existing notes");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // juju delete mynote start




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