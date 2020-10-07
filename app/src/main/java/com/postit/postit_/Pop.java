package com.postit.postit_;

import android.app.Activity;
import android.content.Intent;
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
    private Button request;
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
        request =  (Button) findViewById(R.id.reqBtn);
        requestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String requestedMajorS = requestedMajor.getText().toString().trim();
                final String requestedCourseS = requestedCourse.getText().toString().trim();
                final String requestedChapterS = requestedChapter.getText().toString().trim();
                String requestID = requestsRef.push().getKey();
                if(requestedMajorS.isEmpty()){
                    requestedMajor.setError("please enter major");
                    requestedMajor.requestFocus();
                    return;
                }
                if((requestedMajorS.matches("[0-9]+"))){
                    requestedMajor.setError("please enter valid major");
                    requestedMajor.requestFocus();
                    return;
                }
                if(!(requestedMajorS.matches("[a-zA-Z]+"))){
                    requestedMajor.setError("please enter valid major");
                    requestedMajor.requestFocus();
                    return;
                }

                if(requestedCourseS.isEmpty()){
                    requestedCourse.setError("please enter course");
                    requestedCourse.requestFocus();
                    return;
                }if(requestedChapterS.isEmpty()){
                    requestedChapter.setError("please enter Chapter");
                    requestedChapter.requestFocus();
                    return;
                }

                newRequest = new requests(requestedMajorS,requestedCourseS,requestedChapterS,requestID);

                requestsRef.child(requestID).setValue(newRequest);
                Toast.makeText(Pop.this, "Request send successfully", Toast.LENGTH_LONG).show();

            startActivity(new Intent(Pop.this,StudentActivity.class));

            }
        });

    }
}
