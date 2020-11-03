package com.postit.postit_.Student_Visitor;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.requests;
import com.postit.postit_.R;

public class Pop extends Activity {
    private EditText requestedMajor, requestedCourse, requestedChapter;
    private Button request;
    private Button cancel;
    private FirebaseDatabase database;
    private DatabaseReference requestsRef, notetsRef;
    private requests newRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .5));
        requestedMajor = (EditText) findViewById(R.id.MajorCode);
        requestedCourse = (EditText) findViewById(R.id.CourseCode);
        requestedChapter = (EditText) findViewById(R.id.chapterCode);
        request = (Button) findViewById(R.id.reqBtn);
        cancel = (Button) findViewById(R.id.cancel133);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pop.this, StudentActivity.class));
            }
        });

        database = FirebaseDatabase.getInstance();
        requestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String requestedMajorS = requestedMajor.getText().toString().trim();
                final String requestedCourseS = requestedCourse.getText().toString().trim();
                final String requestedChapterS = requestedChapter.getText().toString().trim();
                String requestID = requestsRef.push().getKey();

                if (requestedMajorS.isEmpty()) {
                    requestedMajor.setError("please enter major");
                    requestedMajor.requestFocus();
                    return;
                }
                if ((requestedMajorS.matches("[0-9]+"))) {
                    requestedMajor.setError("please enter valid major");
                    requestedMajor.requestFocus();
                    return;
                }

                if (!(Pattern.matches("[a-zA-Z]+", requestedMajorS.replaceAll("\\s+", "")))) {
                    requestedMajor.setError("Please enter valid major,\nSymbols not allowed\nDigit not allowed\ne.g. software engineering");
                    requestedMajor.requestFocus();}

                if (requestedCourseS.isEmpty()) {
                    requestedCourse.setError("please enter course");
                    requestedCourse.requestFocus();
                    return;
                }
                if ((requestedCourseS.equalsIgnoreCase(" ")) ||  (requestedCourseS.length() > 15) || (!(requestedCourseS.matches("^.*\\d$")))) {
                    requestedCourse.setError("Please enter valid course \ne.g. SWE321, ethics");
                    requestedCourse.requestFocus();
                    return;
                }
                if (requestedChapterS.isEmpty()) {
                    requestedChapter.setError("please enter Chapter");
                    requestedChapter.requestFocus();
                    return;
                }
                if ((requestedChapterS.equalsIgnoreCase(" ")) || (!(requestedChapterS.toLowerCase().startsWith("chapter"))) || (requestedChapterS.length() > 10) || (!(requestedChapterS.matches("^.*\\d$")))) {
                    requestedChapter.setError("please enter valid chapter follow this format Chapter#\ne.g. Chapter4");
                    requestedChapter.requestFocus();
                    return;
                }
                newRequest = new requests(requestedMajorS, requestedCourseS, requestedChapterS, requestID);


                requestsRef.child(requestID).setValue(newRequest);
                notification();
                Toast.makeText(Pop.this, "Request send successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(Pop.this, StudentActivity.class));

            }
        });

    }

    private void notification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"n")
                .setContentText("Code Sphere")
                .setSmallIcon(R.drawable.ic_baseline_edit_24)
                .setAutoCancel(true)
                .setContentText("there is new request");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }
}