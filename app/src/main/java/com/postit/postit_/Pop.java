package com.postit.postit_;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Pop extends Activity {
    private EditText requestedMajor, requestedCourse, requestedChapter;
    private Button requestChapter;
    private DatabaseReference requestsRef;
    private requests newRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
getWindow().setLayout((int)(width*.8),(int)(height*.5));
        requestedMajor = (EditText) findViewById(R.id.MajorCode);
        requestedCourse = (EditText) findViewById(R.id.CourseCode);
        requestedChapter = (EditText) findViewById(R.id.chapterCode);
        requestChapter =  (Button) findViewById(R.id.reqBtn);
        requestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        requestChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewRequest();
            }
        });

    }
    public void addNewRequest(){
        final String requestedMajorS = requestedMajor.getText().toString().trim();
        final String requestedCourseS = requestedCourse.getText().toString().trim();
        final String requestedChapterS = requestedChapter.getText().toString().trim();
        String requestID = requestsRef.push().getKey();
        newRequest = new requests(requestedMajorS,requestedCourseS,requestedChapterS,requestID);

        requestsRef.child(requestID).setValue(newRequest);
    }
}