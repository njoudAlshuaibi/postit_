package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Visitors_activity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference majorRef;
    private DatabaseReference courseRef;
    private Spinner listView;
    private Spinner courseSpineer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visitors_activity);
        database= FirebaseDatabase.getInstance();
        majorRef=FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef=FirebaseDatabase.getInstance().getReference().child("Courses");
        listView=findViewById(R.id.spinner1);
        final ArrayList<String> majorList=new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitem, majorList);
        listView.setAdapter(adapter);
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                majorList.clear();

                majorList.add("Choose major");
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
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot22) {
                coourseList.clear();
                coourseList.add("Choose Course");

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
    }
}