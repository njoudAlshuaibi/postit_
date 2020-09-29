package com.postit.postit_;

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
import android.widget.Toast;
import java.lang.*;

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

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity  {

private EditText MajorName, CourseID,chapterID;
private ImageButton addMajor, addCoure,addChapter;
private ImageButton deleteMajor, deleteCoure,deleteChapter;
private major newmajor;
private course newCourse;
private FirebaseDatabase database;
private DatabaseReference majorRef;
private DatabaseReference courseRef;
private DatabaseReference chapterRef;
private Button add;




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
        majorRef=database.getReference("Majors");
        courseRef=database.getReference("Courses");
        chapterRef=database.getReference("Chapters");
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
        //lujain


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
        return true;
    }

    public void addNewMajor(){
        final String name= MajorName.getText().toString().trim();
        newmajor =new major ("CCIS",name);
        majorRef.push().setValue(newmajor);
        Toast.makeText(AdminActivity.this, "Major added Successfully ", Toast.LENGTH_LONG).show();
    }
    public void addNewCourse(){
        String courseN=CourseID.getText().toString().trim().toUpperCase();
        String majorCut=courseN.substring(0,2);
        String fullMajor="";
        if(majorCut.equals("IS")){
            fullMajor="Information System";
        }
        if(majorCut.equals("CS")){
            fullMajor="Computer Science";
        }
        if(majorCut.equals("IT")){
            fullMajor="Information Technology";
        }
        if(majorCut.equals("SW")){
            fullMajor="Software Engineering";
        }
        newCourse= new course("CCIS",fullMajor,courseN);
        courseRef.push().setValue(newCourse);
        Toast.makeText(AdminActivity.this, "Course added Successfully ", Toast.LENGTH_LONG).show();

    }
    public void addNewChapter(){

    }

}
