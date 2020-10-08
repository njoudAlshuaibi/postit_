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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class BrowseNotes extends AppCompatActivity {

    private RecyclerView titleList;
    private DatabaseReference noteRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_notes);

        Toolbar tool = findViewById(R.id.toolbar_Browsenotes);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        noteRef= FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef.keepSynced(true);
        titleList=(RecyclerView)findViewById(R.id.myRecycleView);
        titleList.setHasFixedSize(true);
        titleList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<note,NoteViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<note,NoteViewHolder>
                (note.class,R.layout.notedesign,NoteViewHolder.class,noteRef){

            @Override
            protected void populateViewHolder(NoteViewHolder viewHolder, note model , int position){

                viewHolder.setTitle(model.getTitle());

            }

        };

        titleList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public  NoteViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title){
            TextView noteTitleD = (TextView)mView.findViewById(R.id.noteTitle);
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
            startActivity(new Intent(BrowseNotes.this,StudentActivity.class));
        }
        return true;
    }
}