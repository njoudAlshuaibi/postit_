package com.postit.postit_.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.chapter;
import com.postit.postit_.Objects.course;
import com.postit.postit_.Objects.major;
import com.postit.postit_.Objects.note;
import com.postit.postit_.Objects.requests;
import com.postit.postit_.R;
import com.postit.postit_.Student_Visitor.popUpWindow;

import java.util.*;
import java.util.regex.Pattern;


public class AdminActivity extends AppCompatActivity {

    private EditText MajorName, CourseID, chapterID;
    private ImageButton addMajor, addCoure, addChapter;
    private ImageButton deleteMajor, deleteCoure, deleteChapter;
    private major newmajor;
    private course newCourse;
    private DatabaseReference majorRef;
    private DatabaseReference courseRef;
    private DatabaseReference requestsRef, chapterRef, noteRef;

    private Spinner majorSpinner, courseSpineer, chapterSpinner;
    public String courseMajor, chapterCourse, chapterChapter;
private TextView textViewp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Manage");
        toolbar.setTitleTextColor(0xFFB8B8B8);

        Intent intent = getIntent();
        final String maj = intent.getStringExtra(requestadmin.maj);
        final String cou = intent.getStringExtra(requestadmin.cou);
        final String ch = intent.getStringExtra(requestadmin.ch);

        requestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");

        textViewp = (TextView) findViewById(R.id.textViewp);
        ImageButton imageButtonreq = (ImageButton) findViewById((R.id.imageButtonreq));
        ImageButton imageButtoncancel = (ImageButton) findViewById((R.id.imageButtoncancel));

        imageButtoncancel.setVisibility(View.INVISIBLE);
        imageButtonreq.setVisibility(View.INVISIBLE);

        if (maj != null)
        {   textViewp.setText("Major: "+ maj +"  Course: " + cou + "  chapter: "+ ch);
        imageButtonreq.setVisibility(View.VISIBLE);
        imageButtoncancel.setVisibility(View.VISIBLE);
            imageButtoncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AdminActivity.this, requestadmin.class));

                }
            });
        imageButtonreq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    new AlertDialog.Builder(AdminActivity.this)
                            .setIcon(android.R.drawable.ic_input_add)
                            .setTitle("are you done?")
                            .setMessage("Do you add this request successfully?"+
                                    " the request will be deleted")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestsRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot child : snapshot.getChildren()) {
                                                requests findreq = child.getValue(requests.class);
                                                String maj1 = findreq.getRequestedMajor();
                                                String co1 = findreq.getRequestedCourse();
                                                String ch12 = findreq.getRequestedChapter();


                                                if (maj1.equals(maj) && co1.equals(cou) && ch12.equals(ch)) {
                                                    String deletedreq = findreq.getId();
                                                    deleteChapter(deletedreq);
                                                    startActivity(new Intent(AdminActivity.this, requestadmin.class));

                                                    break;
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();


                }

                public void deleteChapter(String chapterKey) {
                    requestsRef.child(chapterKey.trim()).removeValue();
                }
            });
    }
        //lujain
        MajorName = (EditText) findViewById(R.id.MajorName);
        CourseID = (EditText) findViewById(R.id.CourseID);
        chapterID = (EditText) findViewById(R.id.chapterID);
        addMajor = (ImageButton) findViewById(R.id.imageButton7);
        addCoure = (ImageButton) findViewById((R.id.imageButton3));
        addChapter = (ImageButton) findViewById(R.id.imageButton5);
        deleteChapter = (ImageButton) findViewById(R.id.imageButton6);
        majorRef = FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef = FirebaseDatabase.getInstance().getReference().child("Courses");
        chapterRef = FirebaseDatabase.getInstance().getReference().child("Chapters");
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");


        addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMajor();
            }
        });
        addCoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCourse();
            }
        });
        addChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewChapter();
            }
        });
        majorSpinner = findViewById(R.id.spinnerMajor);
        final ArrayList<String> majorList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitem, majorList);
        majorSpinner.setAdapter(adapter);


        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                majorList.clear();
                majorList.add("select");
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
        courseSpineer = findViewById(R.id.spinnerCourse);
        final ArrayList<String> coourseList = new ArrayList<>();
        final ArrayAdapter adapter1 = new ArrayAdapter<String>(this, R.layout.listitem, coourseList);
        courseSpineer.setAdapter(adapter1);

        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseMajor = parent.getItemAtPosition(position).toString();
                courseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot22) {
                        coourseList.clear();
                        coourseList.add("select");
                        for (DataSnapshot courseSnapshot : dataSnapshot22.getChildren()) {
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



        chapterSpinner = findViewById(R.id.spinnerChapter);
        final ArrayList<String> chapterList = new ArrayList<>();
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.listitem, chapterList);
        chapterSpinner.setAdapter(adapter2);

        courseSpineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterCourse = parent.getItemAtPosition(position).toString();
                chapterRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        chapterList.clear();
                        chapterList.add("select");

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
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
        deleteChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FFF = courseMajor;
                String fff = chapterCourse;
                String d = chapterChapter;
                if (fff == "select" || fff == "" || FFF == "" || FFF == "select" || d == "select") {
                    Toast.makeText(AdminActivity.this, "please select major and course, then select chapter to delete ", Toast.LENGTH_LONG).show();
                } else {
                    new AlertDialog.Builder(AdminActivity.this)
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Are you sure?")
                            .setMessage("Do you want to delete this chapter?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    chapterRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot child : snapshot.getChildren()) {
                                                chapter findChapter = child.getValue(chapter.class);
                                                String courseId = findChapter.getCourse();
                                                String chapterId = findChapter.getChapterNum();
                                                String majorID = findChapter.getMajor();


                                                if (courseId.equals(chapterCourse) && chapterId.equals(chapterChapter) && majorID.equals(courseMajor)) {
                                                    String deletedChapter = findChapter.getId();
                                                    deleteChapter(deletedChapter);
                                                    noteRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshothh) {
                                                            for (DataSnapshot childnn : snapshothh.getChildren()) {
                                                                note findnote = childnn.getValue(note.class);
                                                                String notecourse = findnote.getCourse();
                                                                String notechapter = findnote.getChapterNum();


                                                                if (notecourse.equals(chapterCourse) && notechapter.equals(chapterChapter)) {
                                                                    String deletednote = findnote.getId();
                                                                    deleteNote(deletednote);
                                                                    break;
                                                                }

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                    break;
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();


                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(AdminActivity.this, MainActivity.class));

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
            startActivity(new Intent(AdminActivity.this, mainAdmin.class));
        }
        return true;
    }

    public void addNewMajor() {
        boolean flag = true;
        String s = MajorName.getText().toString();
        String name = s.trim();
        String majorID;
        if ((s.length() < 10) || (s.equalsIgnoreCase(" ")) || (s.isEmpty())) {
            MajorName.setError("Please enter valid major,\nEnter full major name\ne.g. software engineering");
            MajorName.requestFocus();

        } else if (!(Pattern.matches("[a-zA-Z]+", s.replaceAll("\\s+", "")))) {
            MajorName.setError("Please enter valid major,\nSymbols not allowed\nDigit not allowed\ne.g. software engineering");
            MajorName.requestFocus();

        } else {
            majorID = majorRef.push().getKey();
            newmajor = new major("CCIS", name, majorID);
            majorRef.child(majorID).setValue(newmajor);
            Toast.makeText(AdminActivity.this, "Major added Successfully ", Toast.LENGTH_LONG).show();
            MajorName.setText("");
        }
    }

    public void addNewCourse() {

        String fff = courseMajor;
        if (fff == "select" || fff == "") {
            Toast.makeText(AdminActivity.this, "can't be added , please select major ", Toast.LENGTH_LONG).show();
        } else {
            String courseN = CourseID.getText().toString();
            String v = courseN.trim();
            if (v.equalsIgnoreCase(" ") || v.isEmpty()) {
                CourseID.setError("Please enter valid course code,\ne.g. SWE434 ");
                CourseID.requestFocus();
            } else if (!(v.matches(".*([a-zA-Z].*[0-9]|[0-9].*[a-zA-Z]).*")) || (v.length() > 8)) {
                CourseID.setError("Please enter valid course code,\ne.g. SWE434");
                CourseID.requestFocus();
            } else {
                String id = courseRef.push().getKey();
                course courObj = new course("CCIS", fff, courseN, id);
                courseRef.child(id).setValue(courObj);
                Toast.makeText(AdminActivity.this, "Course added Successfully ", Toast.LENGTH_LONG).show();
                CourseID.setText("");
            }
        }
    }

    public void addNewChapter() {
        String FFF = courseMajor;
        String fff = chapterCourse;
        if (fff == "select" || fff == "" || FFF == "" || FFF == "select") {
            Toast.makeText(AdminActivity.this, "can't be added , please select major and course ", Toast.LENGTH_LONG).show();
        } else {
            String chapterN = chapterID.getText().toString();
            String h = chapterN.trim();
            if ((h.equalsIgnoreCase(" ")) || (h.isEmpty()) || (!(h.startsWith("Chapter"))) || (h.length() > 10) || (!(chapterN.matches("^.*\\d$")))) {
                chapterID.setError("Please enter valid chapter follow this format: Chapter#\ne.g. Chapter4");
                chapterID.requestFocus();
            } else {
                String id = chapterRef.push().getKey();
                chapter chapObj = new chapter("CCIS", FFF, fff, chapterN, id);
                chapterRef.child(id).setValue(chapObj);
                Toast.makeText(AdminActivity.this, "chapter added Successfully ", Toast.LENGTH_LONG).show();
                chapterID.setText("");
            }
        }
    }

    public void deleteChapter(String chapterKey) {
        chapterRef.child(chapterKey.trim()).removeValue();
        Toast.makeText(AdminActivity.this, "chapter deleted Successfully ", Toast.LENGTH_LONG).show();
    }
    public void deleteNote(String noteKey){
        noteRef.child(noteKey.trim()).removeValue();
    }


}
// jujuju
//lu