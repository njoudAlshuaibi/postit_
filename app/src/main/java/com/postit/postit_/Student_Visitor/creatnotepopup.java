package com.postit.postit_.Student_Visitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.postit.postit_.Objects.chapter;
import com.postit.postit_.Objects.course;
import com.postit.postit_.Objects.major;
import com.postit.postit_.Objects.note;
import com.postit.postit_.R;


public class creatnotepopup extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference majorRef;
    private DatabaseReference courseRef;
    private Spinner majorSpinner;
    private Spinner courseSpineer;
    private String courseMajor;
    private Button d;
    private DatabaseReference chapterRef;
    private DatabaseReference noteRef;
    private Button add;
    private Spinner chapterSpinner;
    public String chapterCourse, chapterChapter;
    private EditText inputNoteTitle, inputNoteBody;
    private String userEmail;
    final note noteObj = new note();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnotepopup);

        DisplayMetrics ja = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ja);

        int width = ja.widthPixels;
        int height = ja.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        majorRef = FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef = FirebaseDatabase.getInstance().getReference().child("Courses");
        chapterRef = FirebaseDatabase.getInstance().getReference().child("Chapters");
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        majorSpinner = findViewById(R.id.spinnerA);

        final ArrayList<String> majorList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitem, majorList);
        majorSpinner.setAdapter(adapter);
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                majorList.clear();

                majorList.add("Select");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    major majorObj = postSnapshot.getValue(major.class);
                    majorList.add(majorObj.getMajorName());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        courseSpineer = findViewById(R.id.spinnerAd);
        final ArrayList<String> coourseList = new ArrayList<>();
        final ArrayAdapter adapter1 = new ArrayAdapter<String>(this, R.layout.listitem, coourseList);
        courseSpineer.setAdapter(adapter1);

        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseMajor = parent.getItemAtPosition(position).toString();
                courseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        coourseList.clear();
                        coourseList.add("Select");

                        for (DataSnapshot courseSnapshot : dataSnapshot2.getChildren()) {
                            course courseObj = courseSnapshot.getValue(course.class);
                            String x = courseObj.getMajorName();
                            if (x.equalsIgnoreCase(courseMajor))
                                coourseList.add(courseObj.getCourseName());


                        }
                        adapter1.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
        chapterSpinner = findViewById(R.id.spinnerAdmaaa);
        final ArrayList<String> chapterList = new ArrayList<>();
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.listitem, chapterList);
        chapterSpinner.setAdapter(adapter2);
//
        courseSpineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterCourse = parent.getItemAtPosition(position).toString();
                chapterRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot3) {
                        chapterList.clear();
                        chapterList.add("select");

                        for (DataSnapshot postSnapshot : dataSnapshot3.getChildren()) {
                            chapter chapterObj = postSnapshot.getValue(chapter.class);

                            String x = chapterObj.getCourse();
                            if (x.equalsIgnoreCase(chapterCourse))
                                chapterList.add(chapterObj.getChapterNum());
                        }
                        adapter2.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        chapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterChapter = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inputNoteTitle = findViewById(R.id.Title);
        inputNoteBody = findViewById(R.id.editTextTextMultiLine2);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        } else {
            // No user is signed in
        }

        add = findViewById(R.id.ResetButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

    }

    private void saveNote() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (courseMajor.equals("select") || courseMajor == "" || chapterCourse == "" || chapterCourse.equals("select") || chapterChapter.equals("select") || chapterChapter == "") {
            Toast.makeText(creatnotepopup.this, "can't be added , please select major, course and chapter ", Toast.LENGTH_LONG).show();
        } else if (inputNoteTitle.getText().toString().trim().isEmpty()||(inputNoteTitle.getText().toString().trim().matches("[0-9]+"))) {
            Toast.makeText(creatnotepopup.this, "Note title can not be empty! and it must contains letter", Toast.LENGTH_LONG).show();
            return;
        } else if(inputNoteTitle.getText().toString().trim().length()>35){
            Toast.makeText(creatnotepopup.this, "Note title can not be more than 35 character!", Toast.LENGTH_LONG).show();
            return;
        }
        else if(inputNoteBody.getText().toString().trim().isEmpty()) {
            Toast.makeText(creatnotepopup.this, "Note body can not be empty!", Toast.LENGTH_LONG).show();
            return;
        } else if(inputNoteBody.getText().toString().trim().length()>300){
            Toast.makeText(creatnotepopup.this, "Note body can not be more than 300 character!", Toast.LENGTH_LONG).show();
            return;
        } else if (currentUser != null) {
            String id = noteRef.push().getKey();
            noteObj.setTitle(inputNoteTitle.getText().toString());
            noteObj.setCaption(inputNoteBody.getText().toString());
            noteObj.setCollege("CCIS");
            noteObj.setMajor(courseMajor);
            noteObj.setCourse(chapterCourse);
            noteObj.setChapterNum(chapterChapter);
            noteObj.setId(id);
            noteObj.setEmail(userEmail);
            noteObj.setRate(0f);
            noteObj.setRatingCount(0);

            noteRef.child(id).setValue(noteObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(creatnotepopup.this, "note added successfully", Toast.LENGTH_LONG).show();
                        //             startActivity(new Intent(creatnotepopup.this, ExplorerNote.class));
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(creatnotepopup.this, "note doesn't added", Toast.LENGTH_LONG).show();
                    }

                }
            });
        } else {
            Toast.makeText(creatnotepopup.this, "you need to login first", Toast.LENGTH_LONG).show();
        }

    }


}