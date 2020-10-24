package com.postit.postit_.Student_Visitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
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
import com.postit.postit_.Adapter.BrowseNoteAdapter;
import com.postit.postit_.Objects.course;
import com.postit.postit_.Objects.favoriteList;
import com.postit.postit_.Objects.note;
import com.postit.postit_.R;

public class previewnote extends AppCompatActivity {
    TextView notepre;
    TextView notepr2;
    TextView notetit;
    RatingBar ratingBar;
    Button btnSubmit;
    private DatabaseReference notesRef,favouriteRef;
    float rate;
    int rateCount;
    float currentRate;
    float counter;
    float newrate;
    String s;
    float ratenum;
    boolean flag;
    boolean f;
String userEmail;
    String Nobjid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewnote);
        notepre= findViewById(R.id.notepre);
        notepr2= findViewById(R.id.notepr2);
        notetit= findViewById(R.id.notetit);

        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        favouriteRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");


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
        final String r = intent.getStringExtra(mynotes.prerate);
        final float rate = Float.parseFloat(r);
        final String ratec = intent.getStringExtra(mynotes.precrate);
        final int rateCount = Integer.parseInt(ratec);
        final String ra = intent.getStringExtra(mynotes.precratenum);
        ratenum = Float.parseFloat(ra);

        Intent intenta = getIntent();
        final String pre = intenta.getStringExtra(favoritelist.precc);


        notetit.setText(title);
        notepre.setText("\n\n\n caption :\n\n"+caption);
        notepr2.setText( "\n\n\n\nwritten by : "+ email);

        ratingBar = findViewById(R.id.rating_bar);
        btnSubmit = findViewById(R.id.submitRate);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail().trim();
        } else {
            // No user is signed in
        }

        if(pre.equals("false")){
            btnSubmit.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);}
else{
            btnSubmit.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.VISIBLE);}

        if(email.equals(userEmail)){
            btnSubmit.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);}
        if(user==null){
            btnSubmit.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);}


        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    note noteObj = noteSnapshot.getValue(note.class);
                    String x = noteObj.getId();
                    if (x.equals(id)) {
                        flag = true;
                        break;
                    }else{
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    s = String.valueOf(ratingBar.getRating());
                    Toast.makeText(getApplicationContext(), s + "Star", Toast.LENGTH_SHORT).show();

                    if(flag==true) {
                        currentRate = Float.parseFloat(s);
//                float c = currentRate + rate;
                        ratenum = ratenum + currentRate;
                        counter = (float) rateCount + 1;
                        newrate = (ratenum / counter);
                        notesRef.child(id).child("allrates").setValue(ratenum);
                        notesRef.child(id).child("rate").setValue(newrate);
                        notesRef.child(id).child("ratingCount").setValue(counter);


                    }



                }


            });



    }




}