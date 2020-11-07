package com.postit.postit_.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Objects.chapter;
import com.postit.postit_.Objects.course;
import com.postit.postit_.Objects.major;
import com.postit.postit_.R;

import java.util.ArrayList;

public class PopUpWindowAdmin extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference majorRef;
    private DatabaseReference courseRef, noteRef;
    private Spinner majorSpinner;
    private Spinner courseSpineer;
    private String courseMajor;
    private String coursename;
    private DatabaseReference chapterRef;
    private Button d;
    private TextView browse;
    private Spinner chapterSpinner;
    public String chapterCourse, chapterChapter;
    public static final String adminMajorChoicee = "com.postit.postit_.adminMajorChoicee";
    public static final String adminCourceChoicee = "com.postit.postit_.adminCourceChoicee";
    public static final String adminChapterChoicee = "com.postit.postit_.adminChapterChoicee";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindowsadmin);

        DisplayMetrics ma = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ma);

        int width = ma.widthPixels;
        int height = ma.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .7));

        // retrieve data

        database = FirebaseDatabase.getInstance();
        majorRef = FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef = FirebaseDatabase.getInstance().getReference().child("Courses");
        chapterRef = FirebaseDatabase.getInstance().getReference().child("Chapters");
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");


        majorSpinner = findViewById(R.id.spinnerAdmin);
        browse = findViewById(R.id.browseAdmin);


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
        courseSpineer = findViewById(R.id.spinnerAdmin2);
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


        chapterSpinner = findViewById(R.id.spinnerAdmin3);
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

            public void onClick(View view) {


                if ((courseMajor =="") || (courseMajor == "select") || (chapterCourse =="") || (chapterCourse == "select") || (chapterChapter == null) || (chapterChapter == "select")) {

                    Toast.makeText(PopUpWindowAdmin.this, "please select major and course and chapter", Toast.LENGTH_LONG).show();

                }
                else {
                    Intent intent = new Intent(PopUpWindowAdmin.this, admin_browse_notes.class);
                    intent.putExtra(adminMajorChoicee, courseMajor);
                    intent.putExtra(adminCourceChoicee, chapterCourse);
                    intent.putExtra(adminChapterChoicee, chapterChapter);


                    startActivity(intent);
                }

            }//End onClick()
        });

    }
}