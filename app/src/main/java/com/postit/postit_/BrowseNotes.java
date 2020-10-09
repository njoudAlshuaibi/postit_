package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class BrowseNotes extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.postit.postit_.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.postit.postit_.EXTRA_TEXT2";
    private RecyclerView titleList;
    private DatabaseReference noteRef;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_notes);

        Toolbar tool = findViewById(R.id.toolbar_Browsenotes);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAuth = FirebaseAuth.getInstance();

        noteRef= FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef.keepSynced(true);
        titleList=(RecyclerView)findViewById(R.id.myRecycleView);
        titleList.setHasFixedSize(true);
        titleList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions option = new FirebaseRecyclerOptions.Builder().setQuery(noteRef,note.class).build();
        FirebaseRecyclerAdapter<note,NoteViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<note,NoteViewHolder>
                (option){

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notedesign,parent,false);

                return new NoteViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull final note note) {

                noteViewHolder.setTitle(note.getTitle());
                noteViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = note.getId();
                        String a = note.getCaption();

                        Intent n = new Intent(BrowseNotes.this, previewnote.class);
                        n.putExtra(EXTRA_TEXT,""+s );
                        n.putExtra(EXTRA_TEXT2,""+a );

                        startActivity(n);

                    }
                });         }

//            @Override
//            protected void populateViewHolder(NoteViewHolder viewHolder, note model , int position){
//
//                viewHolder.setTitle(model.getTitle());
//            }


        };
        firebaseRecyclerAdapter.startListening();
        titleList.setAdapter(firebaseRecyclerAdapter);

    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder{

        View mView;
        TextView noteTitleD ;


        public  NoteViewHolder(View itemView){
            super(itemView);
            mView=itemView;


        }

        public void setTitle(String title){
            noteTitleD = (TextView)mView.findViewById(R.id.noteTitle);
            noteTitleD.setText(title);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mynotes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BrowseNotes.this);
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(BrowseNotes.this, MainActivity.class));
                    finish();
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
            startActivity(new Intent(BrowseNotes.this,StudentActivity.class));
        }else {
                new AlertDialog.Builder(BrowseNotes.this)
                        .setTitle("you need to login first")
                        .setMessage("Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent (BrowseNotes.this,MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        }
        return true;
    }
}