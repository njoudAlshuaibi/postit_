package com.postit.postit_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class popupwindowchapters extends AppCompatActivity {
    private Button Post;
    private EditText Title;
    private EditText caption;
    final note noteObj = new note();
    private DatabaseReference noteRef;
    private FirebaseUser user;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindowchapters);

        DisplayMetrics ja = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ja);



        int width = ja.widthPixels;
        int height = ja.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        Intent intent = getIntent();
        final String MajorN = intent.getStringExtra(BrowseNotes.EXTRA_TEXT7);
        final String CourseN = intent.getStringExtra(BrowseNotes.EXTRA_TEXT8);
        final String ChapterN = intent.getStringExtra(BrowseNotes.EXTRA_TEXT9);
        Title=(EditText) findViewById(R.id.Title);
        caption = (EditText) findViewById(R.id.editTextTextMultiLine2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        } else {
            // No user is signed in
        }
        Post = findViewById(R.id.post2);
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MajorN.equals("select") || MajorN == "" || CourseN == "" || CourseN.equals("select") || ChapterN.equals("select") || ChapterN == "") {
                    Toast.makeText(popupwindowchapters.this, "can't be added , please select major, course and chapter ", Toast.LENGTH_LONG).show();
                } else if (Title.getText().toString().trim().isEmpty()) {
                    Toast.makeText(popupwindowchapters.this, "Note title can not be empty!", Toast.LENGTH_LONG).show();
                    return;
                } else if (caption.getText().toString().trim().isEmpty()) {
                    Toast.makeText(popupwindowchapters.this, "Note body can not be empty!", Toast.LENGTH_LONG).show();
                    return;
                } else if (user != null) {
                    String id = noteRef.push().getKey();
                    noteObj.setTitle(Title.getText().toString());
                    noteObj.setCaption(caption.getText().toString());
                    noteObj.setCollege("CCIS");
                    noteObj.setMajor(MajorN);
                    noteObj.setCourse(CourseN);
                    noteObj.setChapterNum(ChapterN);
                    noteObj.setId(id);
                    noteObj.setEmail(userEmail);

                    noteRef.child(id).setValue(noteObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(popupwindowchapters.this, "note added successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(popupwindowchapters.this, BrowseNotes.class));
                            } else {
                                Toast.makeText(popupwindowchapters.this, "note doesn't added", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(popupwindowchapters.this, "you need to login first", Toast.LENGTH_LONG).show();
                }
            }
        });

// lululululululu

    }

}