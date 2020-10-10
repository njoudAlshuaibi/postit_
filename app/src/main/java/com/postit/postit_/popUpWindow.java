package com.postit.postit_;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class popUpWindow extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference majorRef;
    private DatabaseReference courseRef;
    private Spinner majorSpinner;
    private Spinner courseSpineer;
    private String courseMajor;
    private Button browse ;
    private String coursename;
    private DatabaseReference chapterRef, noteRef;
    private FirebaseUser user;
    String majo;


    private Spinner chapterSpinner;
    public String chapterCourse, chapterChapter;

    public static final String EXTRA_TEXT4 = "com.postit.postit_.EXTRA_TEXT4";
    public static final String EXTRA_TEXT5 = "com.postit.postit_.EXTRA_TEXT5";
    public static final String EXTRA_TEXT6 = "com.postit.postit_.EXTRA_TEXT6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        DisplayMetrics sa = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(sa);

        int width = sa.widthPixels;
        int height = sa.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .7));

        database= FirebaseDatabase.getInstance();
        majorRef=FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef=FirebaseDatabase.getInstance().getReference().child("Courses");
        chapterRef=FirebaseDatabase.getInstance().getReference().child("Chapters");
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        user = FirebaseAuth.getInstance().getCurrentUser();


        majo="";
        majo=user.getUid();

        majorSpinner=findViewById(R.id.spinner1a);
        browse = findViewById(R.id.browsen);

        final ArrayList<String> majorList=new ArrayList<>();
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
        courseSpineer=findViewById(R.id.spinner2b);
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
                        coourseList.add("select");

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

        chapterSpinner = findViewById(R.id.spinner3c);
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


        browse.setOnClickListener(new View.OnClickListener() {
            boolean flag=false;

            public void onClick(View view) {

                if ((courseMajor.isEmpty())||(courseMajor == "select")||(chapterCourse.isEmpty())||(chapterCourse == "select")||(chapterChapter.isEmpty())||(chapterChapter == "select")){

                    Toast.makeText(popUpWindow.this, "please select major and course and chapter", Toast.LENGTH_LONG).show();

                }

                    else {
                        noteRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshotrf) {
                                for (DataSnapshot childrf : snapshotrf.getChildren()) {
                                    note findNote = childrf.getValue(note.class);
                                    String courseId = findNote.getCourse();
                                    String chapterId = findNote.getChapterNum();
                                    if (courseId.equals(chapterCourse) && chapterId.equals(chapterChapter)) {
                                        flag = true;
                                        break;
                                    }
                                    else {
                                        Toast.makeText(popUpWindow.this, "no existing notes", Toast.LENGTH_LONG).show();

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                 if(flag){

                    Intent intent = new Intent(popUpWindow.this, BrowseNotes.class);
                    intent.putExtra(EXTRA_TEXT4, courseMajor);
                    intent.putExtra(EXTRA_TEXT5, chapterCourse);
                    intent.putExtra(EXTRA_TEXT6, chapterChapter);

                    startActivity(intent);

//                }
                } }//End onClick()
        });
    }
}
