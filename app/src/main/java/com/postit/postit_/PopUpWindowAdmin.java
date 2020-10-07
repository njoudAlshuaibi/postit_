package com.postit.postit_;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PopUpWindowAdmin extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference majorRef;
    private DatabaseReference courseRef;
    private Spinner majorSpinner;
    private Spinner courseSpineer;
    private String courseMajor;
    private String coursename;
    private Button d ;
    private TextView browse;
    public static final String EXTRA_TEXT = "com.postit.postit_.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.postit.postit_.EXTRA_TEXT2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindowsadmin);

        DisplayMetrics ma = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ma);

        int width = ma.widthPixels;
        int height = ma.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        // retrieve data

        database= FirebaseDatabase.getInstance();
        majorRef=FirebaseDatabase.getInstance().getReference().child("Majors");
        courseRef=FirebaseDatabase.getInstance().getReference().child("Courses");
        majorSpinner =findViewById(R.id.spinnerAdmin);
        browse = findViewById(R.id.browseAdmin);



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
        courseSpineer=findViewById(R.id.spinnerAdmin2);
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



        browse.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if ((courseMajor == "")||(courseMajor == "select")||(coursename == "")||(coursename == "select")){

                    Toast.makeText(PopUpWindowAdmin.this, "please select major and course", Toast.LENGTH_LONG).show();

                }
                else {

    Intent intent = new Intent(PopUpWindowAdmin.this, admin_browse_notes.class);
    intent.putExtra(EXTRA_TEXT, courseMajor);
    intent.putExtra(EXTRA_TEXT2, coursename);

    startActivity(intent);

}  }//End onClick()
        });

    }
}


