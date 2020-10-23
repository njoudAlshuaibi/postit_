package com.postit.postit_.Student_Visitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Adapter.BrowseNoteAdapter;
import com.postit.postit_.Objects.course;
import com.postit.postit_.Objects.note;
import com.postit.postit_.R;

public class previewnote extends AppCompatActivity {
    TextView notepre;

    TextView notepr2;
    TextView notetit;
    RatingBar ratingBar;
    Button btnSubmit;
    private DatabaseReference notesRef;


    public static final String EXTRA_rateNum = "com.postit.postit_.EXTRA_rateNum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewnote);
        notepre= findViewById(R.id.notepre);
        notepr2= findViewById(R.id.notepr2);
        notetit= findViewById(R.id.notetit);

        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");

        DisplayMetrics aa = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(aa);

//
//        int width = aa.widthPixels;
//        int height = aa.heightPixels;
//        getWindow().setLayout((int) (width * .8), (int) (height * .7));

        Intent intent = getIntent();

        final String title = intent.getStringExtra(mynotes.preTitel);
        final String caption = intent.getStringExtra(mynotes.preCaption);
        final String email = intent.getStringExtra(mynotes.preEmail);
        final String id = intent.getStringExtra(mynotes.preID);


        notetit.setText(title);
        notepre.setText("\n\n\n caption :\n\n"+caption);
        notepr2.setText( "\n\n\n\n\n\n\n\n\n\n written by : "+ email);

        ratingBar = findViewById(R.id.rating_bar);
        btnSubmit = findViewById(R.id.submitRate);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(),s+"Star",Toast.LENGTH_SHORT).show();



                double rate = Double.parseDouble(findRate(id).trim());
                int rateCount = Integer.parseInt(findRateCount(id).trim())+1;
                double newRate = Float.parseFloat(s);
                rate = rate + newRate;
                rate = rate/rateCount;
                notesRef.child(id).child("rate").setValue(rate);
                notesRef.child(id).child("ratingCount").setValue(rateCount);


                Intent intent = new Intent(previewnote.this, ExplorerNote.class);
                intent.putExtra(EXTRA_rateNum, s);




            }
        });



    }

    public String findRate( final String nI) {

        final String[] xx = new String[1];

        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    note noteObj = noteSnapshot.getValue(note.class);
                    String x = noteObj.getId();
                    if (x.trim().equals(nI)) {
                      xx[0] = ("")+noteObj.getRate();
                            break;


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String j = xx[0];
        return j;
    }

    public String findRateCount( final String nI) {

        final String[] xx = new String[1];

        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    note noteObj = noteSnapshot.getValue(note.class);
                    String x = noteObj.getId();
                    if (x.trim().equals(nI)) {
                        xx[0] = ("")+noteObj.getRatingCount();
                        break;


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String j = xx[0];
        return j;
    }

}