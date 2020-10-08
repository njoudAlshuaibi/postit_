package com.postit.postit_;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

import android.os.AsyncTask;


public class creatnotepopup extends AppCompatActivity {

        private FirebaseDatabase database;
        private FirebaseUser user;
        private DatabaseReference majorRef;
        private DatabaseReference courseRef;
        private Spinner majorSpinner;
        private Spinner courseSpineer;
        private String courseMajor;
        private Button d ;
    private DatabaseReference chapterRef;
    private DatabaseReference noteRef;
    private Button add;
    private Spinner chapterSpinner;
    public String chapterCourse, chapterChapter;
    private EditText inputNoteTitle, inputNoteBody;
    private String userEmail;
    final note noteObj = new note();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnotepopup);

        DisplayMetrics ja = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ja);

        int width = ja.widthPixels;
        int height = ja.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));

        database= FirebaseDatabase.getInstance();
        majorRef=FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef=FirebaseDatabase.getInstance().getReference().child("Courses");
        chapterRef=FirebaseDatabase.getInstance().getReference().child("Chapters");
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        majorSpinner=findViewById(R.id.spinnerA);

        final ArrayList<String> majorList=new ArrayList<>();
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
        courseSpineer=findViewById(R.id.spinnerAd);
        final ArrayList<String> coourseList =new ArrayList<>();
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
//
//                else
//                     coourseList.add("juju");

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
        chapterSpinner=findViewById(R.id.spinnerAdmaaa);
        final ArrayList<String> chapterList=new ArrayList<>();
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.listitem, chapterList);
        chapterSpinner.setAdapter(adapter2);
//
        courseSpineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterCourse= parent.getItemAtPosition(position).toString();
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
                chapterChapter= parent.getItemAtPosition(position).toString();
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
    private void saveNote(){

        if(inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(creatnotepopup.this, "Note title can not be empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(inputNoteBody.getText().toString().trim().isEmpty()){
            Toast.makeText(creatnotepopup.this, "Note body can not be empty!", Toast.LENGTH_LONG).show();
            return;}

       String id =  noteRef.push().getKey();
            noteObj.setTitle(inputNoteTitle.getText().toString());
            noteObj.setCaption(inputNoteBody.getText().toString());
            noteObj.setCollege("CCIS");
            noteObj.setMajor(courseMajor);
            noteObj.setCourse(chapterCourse);
            noteObj.setChapterNum(chapterChapter);
            noteObj.setId(id);
            noteObj.setEmail(userEmail);

        noteRef.child(id).setValue(noteObj);
        Toast.makeText(creatnotepopup.this, "note added successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(creatnotepopup.this,mynotes.class));
        }





      }




