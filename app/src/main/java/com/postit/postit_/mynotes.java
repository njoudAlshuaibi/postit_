package com.postit.postit_;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mynotes extends AppCompatActivity {
    private FloatingActionButton fab;
    private note b;
    private FirebaseDatabase database;
    private DatabaseReference notesRef;
    private DatabaseReference ref;
    public static final String EXTRA_TEXTww = "com.postit.postit_.EXTRA_TEXT";
    public static final String EXTRA_TEXT2ee = "com.postit.postit_.EXTRA_TEXT2";
    private RecyclerView titleList;
    String curUserEmail="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynotes);


        Toolbar toolba = findViewById(R.id.toolbar_mynotes);
        setSupportActionBar(toolba);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        curUserEmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        titleList = (RecyclerView) findViewById(R.id.myRecycleViewee);
        titleList.setHasFixedSize(true);
        titleList.setLayoutManager(new LinearLayoutManager(this));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencreatnotepopup();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions option = new FirebaseRecyclerOptions.Builder().setQuery(notesRef, note.class).build();
        FirebaseRecyclerAdapter<note, BrowseNotes.NoteViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<note, BrowseNotes.NoteViewHolder>
                (option) {
            @NonNull
            @Override
            public BrowseNotes.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v;
                BrowseNotes.NoteViewHolder x = null;
                ViewGroup s = parent;
                v = LayoutInflater.from(s.getContext()).inflate(R.layout.notedesign, s, false);
                x = new BrowseNotes.NoteViewHolder(v);


                return x;
            }

            @Override
            protected void onBindViewHolder(@NonNull BrowseNotes.NoteViewHolder noteViewHolder, int i, @NonNull final note note) {
                if (note.getEmail().equals(curUserEmail)) {
                    noteViewHolder.setTitle(note.getDate());
                    noteViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String s = note.getTitle();
                            String a = note.getCaption();

                            Intent n = new Intent(mynotes.this, previewnote.class);
                            n.putExtra(EXTRA_TEXTww, "" + s);
                            n.putExtra(EXTRA_TEXT2ee, "" + a);

                            startActivity(n);

                        }
                    });
                }
            }


        };
        firebaseRecyclerAdapter.startListening();
        titleList.setAdapter(firebaseRecyclerAdapter);
    }

    private void opencreatnotepopup() {
        Intent popupwindow4 = new Intent(mynotes.this, creatnotepopup.class);
        startActivity(popupwindow4);
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
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(mynotes.this, MainActivity.class));
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
            startActivity(new Intent(mynotes.this, StudentActivity.class));
        }
        return true;
    }
}