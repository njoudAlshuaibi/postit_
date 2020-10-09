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
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BrowseNotes extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.postit.postit_.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.postit.postit_.EXTRA_TEXT2";
    private RecyclerView titleList;
    private DatabaseReference noteRef;
    private FirebaseAuth mAuth;
    private FloatingActionButton fab2;





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

        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpopupwindowchapters();
            }
        });

    }

    private void openpopupwindowchapters() {

        Intent popupwindow5 = new Intent(BrowseNotes.this,popupwindowchapters.class);
        startActivity(popupwindow5);
    }

    @Override
    protected void onStart() {
        Boolean w;


        Intent intent = getIntent();
        final String MajorN = intent.getStringExtra(popupwindowvisitors.EXTRA_TEXT);
        final String CourseN = intent.getStringExtra(popupwindowvisitors.EXTRA_TEXT2);
        final String ChapterN = intent.getStringExtra(popupwindowvisitors.EXTRA_TEXT3);
        final ArrayList<note> noteList = new ArrayList<>();
        final ArrayList<note> note = new ArrayList<>();

        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot3) {
                noteList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot3.getChildren()) {
                    note noteobj = postSnapshot.getValue(note.class);

                    if(noteobj.getCourse().equalsIgnoreCase(CourseN)&&noteobj.getMajor().equalsIgnoreCase(MajorN)&&noteobj.getChapterNum().equalsIgnoreCase(ChapterN))
                        noteList.add(noteobj);
                }
//                adapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        super.onStart();
        FirebaseRecyclerOptions option = new FirebaseRecyclerOptions.Builder().setQuery(noteRef,note.class).build();
//        final ArrayAdapter adapter2 = new ArrayAdapter<note>(this, R.layout.listitem, noteList);
//        chapterSpinner.setAdapter(adapter2);


        FirebaseRecyclerAdapter<note,NoteViewHolder>firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<note,NoteViewHolder>
                (option){


            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                int qq = noteList.size();
                View v;
                NoteViewHolder x = null;
                viewType = qq;
                ViewGroup s = parent;
                s.removeViewsInLayout(1,2);
                ViewGroup aw = s;
//s.removeViewAt(3);
//                parent.removeViews(1,6);

                v = LayoutInflater.from(aw.getContext()).inflate(R.layout.notedesign,aw,false);
                x = new NoteViewHolder(v);


                return x;
            }

            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull final note note) {
                if(note.getCourse().equalsIgnoreCase(CourseN)&&note.getMajor().equalsIgnoreCase(MajorN)&&note.getChapterNum().equalsIgnoreCase(ChapterN))
                { noteViewHolder.setTitle(note.getTitle());
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
                    });    }
//            else
//                noteViewHolder.mView.setVisibility(View.GONE);
            }

//            @Override
//            protected void populateViewHolder(NoteViewHolder viewHolder, note model , int position){
//
//                viewHolder.setTitle(model.getTitle());
//            }


        };
        firebaseRecyclerAdapter.startListening();
        titleList.setAdapter(firebaseRecyclerAdapter);

//            for(int i = 0 ; i< noteList.size(); i++){
//                if(firebaseRecyclerAdapter.getRef(i).equals(noteList.get(i).getId()))
//                {note.add(noteList.get(i));}}
//
//        for(int i = 0 ; i< noteList.size(); i++){
//            if(!firebaseRecyclerAdapter.getRef(i).equals(note.get(i).getId()))
//                firebaseRecyclerAdapter.getRef(i).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    //Your data is removed successfully!
//                }
//            }
//        });}


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
}//rename commit