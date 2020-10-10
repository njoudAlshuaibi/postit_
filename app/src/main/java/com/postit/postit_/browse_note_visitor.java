package com.postit.postit_;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class browse_note_visitor extends AppCompatActivity {
        public static final String EXTRA_TEXTLM = "com.postit.postit_.EXTRA_TEXT";
        public static final String EXTRA_TEXT2LC = "com.postit.postit_.EXTRA_TEXT2";
    public static final String EXTRA_TEXT2LCe = "com.postit.postit_.EXTRA_TEXT3";

    public static final String EXTRA_TEXT7LC = "com.postit.postit_.EXTRA_TEXT7";


        private RecyclerView titleList;
        private DatabaseReference noteRef;
        private FirebaseAuth mAuth;
        private FloatingActionButton fab3;
        String m;
        String c;
        String d;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_browse_note_visitor);

            Toolbar tool = findViewById(R.id.toolbar_Vistor);
            setSupportActionBar(tool);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mAuth = FirebaseAuth.getInstance();
            noteRef= FirebaseDatabase.getInstance().getReference().child("Notes");
            noteRef.keepSynced(true);
            titleList=(RecyclerView)findViewById(R.id.myRecycleView2);
            titleList.setHasFixedSize(true);
            titleList.setLayoutManager(new LinearLayoutManager(this));

            fab3 = (FloatingActionButton) findViewById(R.id.fab3);
            fab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(browse_note_visitor.this)
                            .setTitle("you need to login first")
                            .setMessage("Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent (browse_note_visitor.this,MainActivity.class));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
            Intent intentuu = getIntent();
            m = intentuu.getStringExtra(popupwindowvisitors.EXTRA_TEXTSM);
            c = intentuu.getStringExtra(popupwindowvisitors.EXTRA_TEXT2SC);
            d = intentuu.getStringExtra(popupwindowvisitors.EXTRA_TEXT3SCH);
        }

        @Override
        protected void onStart() {
            Intent intent = getIntent();

            final String MajorN = intent.getStringExtra(popupwindowvisitors.EXTRA_TEXTSM);
            final String CourseN = intent.getStringExtra(popupwindowvisitors.EXTRA_TEXT2SC);
            final String ChapterN = intent.getStringExtra(popupwindowvisitors.EXTRA_TEXT3SCH);


            super.onStart();
            FirebaseRecyclerOptions option = new FirebaseRecyclerOptions.Builder().setQuery(noteRef,note.class).build();
            FirebaseRecyclerAdapter<note,NoteViewHolder>firebaseRecyclerAdapter= new FirebaseRecyclerAdapter <note, NoteViewHolder>(option){

                @NonNull
                @Override
                public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View v;
                    NoteViewHolder x = null;
                    ViewGroup s = parent;

                    v = LayoutInflater.from(s.getContext()).inflate(R.layout.notedesign,s,false);
                    x = new NoteViewHolder(v);
                    return x;
                }
                @Override
                protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull final note note) {
                    if(note.getCourse().equalsIgnoreCase(CourseN)&&note.getMajor().equalsIgnoreCase(MajorN)&&note.getChapterNum().equalsIgnoreCase(ChapterN))
                    {
                        noteViewHolder.setTitle(note.getTitle(),note.getDate());
//                        noteViewHolder.setTitle(note.getDate());

                        noteViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String title = note.getTitle();
                                String caption = note.getCaption();
                                String email =  note.getEmail();

                                Intent n = new Intent(browse_note_visitor.this, previewnote.class);
                                n.putExtra(EXTRA_TEXTLM,""+ title );
                                n.putExtra(EXTRA_TEXT2LC,""+ caption );
                                n.putExtra(EXTRA_TEXT2LCe,""+ email );


                                startActivity(n);

                            }
                        });    }
                }


            };
            firebaseRecyclerAdapter.startListening();
            titleList.setAdapter(firebaseRecyclerAdapter);


        }


        public static class NoteViewHolder extends RecyclerView.ViewHolder{

            View mView;
            TextView noteTitleD ;
            TextView notedate;

            public  NoteViewHolder(View itemView){
                super(itemView);
                mView=itemView;


            }

            public void setTitle(String title,String date){
                noteTitleD = (TextView)mView.findViewById(R.id.noteTitle);
                notedate = (TextView)mView.findViewById(R.id.notedate);

                noteTitleD.setText(title);
                notedate.setText("\n\n\n\n\n                   "+"                 "+date);
            }


        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.visitor_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id==R.id.home){
                    startActivity(new Intent (browse_note_visitor.this,Visitors_activity.class));
            }
            return true;
        }
    }//rename commit
