package com.postit.postit_;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.*;


public class AdminActivity extends AppCompatActivity  {

private EditText MajorName, CourseID,chapterID;
private ImageButton addMajor, addCoure,addChapter;
private ImageButton deleteMajor, deleteCoure,deleteChapter;
private major newmajor;
private course newCourse;
private FirebaseDatabase database;
private DatabaseReference ref;
private DatabaseReference majorRef;
private DatabaseReference courseRef;
private DatabaseReference chapterRef;
private Button add;
private Spinner majorSpinner,courseSpineer,chapterSpinner;
public String courseMajor,chapterCourse, chapterChapter;
public String majID;


//i love uoy


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        //lujain
        MajorName =(EditText) findViewById(R.id.MajorName);
        CourseID= (EditText) findViewById(R.id.CourseID);
        chapterID= (EditText) findViewById(R.id.chapterID);
        addMajor= (ImageButton) findViewById(R.id.imageButton7);
        addCoure= (ImageButton) findViewById((R.id.imageButton3));
        addChapter= (ImageButton) findViewById(R.id.imageButton5);
        deleteMajor=(ImageButton) findViewById(R.id.imageButton8);
        deleteCoure= (ImageButton) findViewById(R.id.imageButton4);
        deleteChapter= (ImageButton) findViewById(R.id.imageButton6);
        database= FirebaseDatabase.getInstance();
        ref=FirebaseDatabase.getInstance().getReference();
        majorRef=FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef=FirebaseDatabase.getInstance().getReference().child("Courses");
        chapterRef=FirebaseDatabase.getInstance().getReference().child("Chapters");

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
        majorSpinner=findViewById(R.id.spinnerMajor);
        final ArrayList<String> majorList=new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitem, majorList);
        majorSpinner.setAdapter(adapter);
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                majorList.clear();
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
        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseMajor= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        courseSpineer=findViewById(R.id.spinnerCourse);
        final ArrayList<String> coourseList=new ArrayList<>();
        final ArrayAdapter adapter1 = new ArrayAdapter<String>(this, R.layout.listitem, coourseList);
        courseSpineer.setAdapter(adapter1);
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot22) {
                coourseList.clear();
                for (DataSnapshot courseSnapshot : dataSnapshot22.getChildren()) {
                    course courseObj = courseSnapshot.getValue(course.class);
                    coourseList.add(courseObj.getCourseName());
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        //end lujain
        courseSpineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterCourse= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chapterSpinner=findViewById(R.id.spinnerChapter);
        final ArrayList<String> chapterList=new ArrayList<>();
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.listitem, chapterList);
        chapterSpinner.setAdapter(adapter2);
        chapterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chapterList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    chapter chapterObj = postSnapshot.getValue(chapter.class);
                    chapterList.add(chapterObj.getChapterNum());
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        deleteChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chapterRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot child : snapshot.getChildren()){
                            chapter findChapter = child.getValue(chapter.class);
                            String courseId = findChapter.getCourse();
                            String chapterId = findChapter.getChapterNum();
                            String majorID = findChapter.getMajor();

                            if(courseId.equals(chapterCourse) && chapterId.equals(chapterChapter) && majorID.equals(courseMajor)){
                                String deletedChapter =findChapter.getId();
                                deleteChapter(deletedChapter);
                                break;
                            }
                            else{
                                Toast.makeText(AdminActivity.this, "failed to delete chapter", Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    //
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
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(AdminActivity.this, MainActivity.class));
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

        }
        else if (id==R.id.home){
            startActivity(new Intent(AdminActivity.this,mainAdmin.class));
        }
        return true;
    }

    public void addNewMajor() {

        final String name = MajorName.getText().toString().trim();
        String majorID = majorRef.push().getKey();
        newmajor = new major("CCIS", name,majorID);
        majorRef.child(majorID).setValue(newmajor);
        Toast.makeText(AdminActivity.this, "Major added Successfully ", Toast.LENGTH_LONG).show();
        MajorName.setText("");
    }

    public void addNewCourse() {
        String courseN = CourseID.getText().toString().trim();
        String fff =courseMajor;
        String id=courseRef.push().getKey();
        course courObj=new course("CCIS",fff,courseN,id);
        courseRef.child(id).setValue(courObj);
        Toast.makeText(AdminActivity.this, "Course added Successfully ", Toast.LENGTH_LONG).show();
        CourseID.setText("");

    }
    public void addNewChapter(){
        String chapterN = chapterID.getText().toString().trim();
        String FFF =courseMajor;
        String fff =chapterCourse;
        String id=chapterRef.push().getKey();
        chapter chapObj=new chapter("CCIS",FFF,fff,chapterN,id);
        chapterRef.child(id).setValue(chapObj);
        Toast.makeText(AdminActivity.this, "chapter added Successfully ", Toast.LENGTH_LONG).show();
        chapterID.setText("");
    }

    public void deleteChapter(String chapterKey) {
        chapterRef.child(chapterKey.trim()).removeValue();
        Toast.makeText(AdminActivity.this, "chapter deleted Successfully ", Toast.LENGTH_LONG).show();


    }
    ////اشتغل يا حلو


}


