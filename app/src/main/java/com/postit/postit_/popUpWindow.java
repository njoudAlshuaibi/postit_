package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Button reqBtn ;
    private DatabaseReference chapterRef;

    private String coursename;

    private Spinner chapterSpinner;
    public String chapterCourse, chapterChapter;

    public static final String EXTRA_TEXT = "com.postit.postit_.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.postit.postit_.EXTRA_TEXT2";
    public static final String EXTRA_TEXT3 = "com.postit.postit_.EXTRA_TEXT3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        DisplayMetrics ma = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ma);

        int width = ma.widthPixels;
        int height = ma.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        database= FirebaseDatabase.getInstance();
        majorRef=FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef=FirebaseDatabase.getInstance().getReference().child("Courses");
        chapterRef=FirebaseDatabase.getInstance().getReference().child("Chapters");
        majorSpinner=findViewById(R.id.spinner1);
        reqBtn = findViewById(R.id.browsen);

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
        courseSpineer=findViewById(R.id.spinner2);
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
        courseSpineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coursename = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chapterSpinner=findViewById(R.id.spinner3);
        final ArrayList<String> chapterList=new ArrayList<>();
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.listitem, chapterList);
        chapterSpinner.setAdapter(adapter2);

        courseSpineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterCourse= parent.getItemAtPosition(position).toString();
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
                chapterChapter= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reqBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if ((courseMajor == null)||(courseMajor == "select")||(coursename == null)||(coursename == "select")||(chapterChapter == null)||(chapterChapter == "select")){

                    Toast.makeText(popUpWindow.this, "please select major and course and chapter", Toast.LENGTH_LONG).show();

                }
                else {

                    Intent intent = new Intent(popUpWindow.this, BrowseNotes.class);
                    intent.putExtra(EXTRA_TEXT, courseMajor);
                    intent.putExtra(EXTRA_TEXT2, coursename);
                    intent.putExtra(EXTRA_TEXT3, chapterChapter);

                    startActivity(intent);

                }  }//End onClick()
        });

// r
    }
}