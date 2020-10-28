package com.postit.postit_.Student_Visitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Adapter.BrowseCommentAdapter;
import com.postit.postit_.Adapter.BrowseNoteAdapter;
import com.postit.postit_.Admin.AdminActivity;
import com.postit.postit_.Objects.comment;
import com.postit.postit_.Objects.course;
import com.postit.postit_.Objects.favoriteList;
import com.postit.postit_.Objects.note;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;

import java.util.ArrayList;

public class previewnote extends AppCompatActivity {
    TextView notepre;
    TextView notepr2;
    TextView notetit;
    RatingBar ratingBar;
    Button btnSubmit, submitComment;
    private DatabaseReference notesRef, favouriteRef, commentsRef;
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
    private ListView comments;
    BrowseCommentAdapter commentAdapter;
    EditText newComment;
    String scomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewnote);
        notepre = findViewById(R.id.notepre);
        notepr2 = findViewById(R.id.notepr2);
        notetit = findViewById(R.id.notetit);
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        favouriteRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");
        newComment = (EditText) findViewById(R.id.newcomment);
        submitComment= (Button) findViewById(R.id.submitComment);

        commentsRef= FirebaseDatabase.getInstance().getReference().child("Comments");

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
        notepre.setText("caption :\n\n" + caption);
        notepr2.setText("written by : " + email);

        ratingBar = findViewById(R.id.rating_bar);
        btnSubmit = findViewById(R.id.submitRate);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail().trim();
        } else {
            // No user is signed in
        }

        if (pre.equals("false")) {
            btnSubmit.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
        } else {
            btnSubmit.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
        }

        if (email.equals(userEmail)) {
            btnSubmit.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
        }
        if (user == null) {
            btnSubmit.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
        }


        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    note noteObj = noteSnapshot.getValue(note.class);
                    String x = noteObj.getId();
                    if (x.equals(id)) {
                        flag = true;
                        break;
                    } else {
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

                if (flag == true) {
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
        comments = (ListView) findViewById(R.id.commentstt);
        final ArrayList<comment> commentsList = new ArrayList<>();
        commentAdapter = new BrowseCommentAdapter(this,R.layout.adapter_view_layout, commentsList);
        comments.setAdapter(commentAdapter);
        commentsRef = FirebaseDatabase.getInstance().getReference().child("Comments");
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotq) {
                commentsList.clear();
                for (DataSnapshot snapshotc : snapshotq.getChildren()) {
                    comment commentObj = snapshotc.getValue(comment.class);
                    if(commentObj.getNoteID().equals(id)){
                    commentsList.add(commentObj);
                    }

                }//
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        submitComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scomment = newComment.getText().toString().trim();
                String commID= commentsRef.push().getKey();
                comment c= new comment(commID,id,scomment);
                addNewComment(id,c);
            }
        });



    }
    public void addNewComment(String noteID, comment c){
        commentsRef.child(c.getCommID().trim()).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful()){
                   Toast.makeText(previewnote.this, "Your comment added successfully", Toast.LENGTH_LONG).show();
                   newComment.setText("");
               }
            }
        });
    }


}