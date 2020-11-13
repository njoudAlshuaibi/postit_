package com.postit.postit_.Student_Visitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.comment;
import com.postit.postit_.Objects.note;
import com.postit.postit_.Objects.rate;
import com.postit.postit_.R;

import java.util.ArrayList;

public class previewnote extends AppCompatActivity {
    TextView notepre;
    TextView notepr2;
    TextView notetit;
    RatingBar ratingBar,rating_bar2;
    Button btnSubmit, submitComment;
    private DatabaseReference notesRef, favouriteRef, commentsRef, rateRef;
    String rateN;
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
    final rate rateObj = new rate();
    ImageView startChat;
    boolean flagw=false;

    public static final String writerEmail = "com.postit.postit_.writerEmail";
    private FirebaseUser user;
    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");
String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewnote);
        notepre = findViewById(R.id.notepre);
        notepr2 = findViewById(R.id.notepr2);
        notetit = findViewById(R.id.notetit);
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        favouriteRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");
        rateRef = FirebaseDatabase.getInstance().getReference().child("Rates");
        startChat = (ImageView) findViewById(R.id.startChat);
        newComment = (EditText) findViewById(R.id.newcomment);
        submitComment = (Button) findViewById(R.id.submitComment);

        commentsRef = FirebaseDatabase.getInstance().getReference().child("Comments");

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
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !(user.getEmail().equals(email))) {
            startChat.setVisibility(View.VISIBLE);
        } else {
            startChat.setVisibility(View.INVISIBLE);
        }
        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(previewnote.this, chatActivity.class);
                in.putExtra(writerEmail, "" + email);
                startActivity(in);
                //  startActivity(new Intent(previewnote.this, chatActivity.class));
            }
        });
        Intent intenta = getIntent();
        final String pre = intenta.getStringExtra(favoritelist.precc);


        notetit.setText(title);
        notepre.setText("caption :\n\n" + caption);
        g.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    com.postit.postit_.Objects.user usa = postSnapshot.getValue(com.postit.postit_.Objects.user.class);
                    if(usa.getEmail().equals(email)){
                        username = usa.getUsername();
                        notepr2.setText("written by : " + username);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        rating_bar2 = findViewById(R.id.rating_bar2);
        ratingBar = findViewById(R.id.rating_bar);
        btnSubmit = findViewById(R.id.submitRate);
        btnSubmit.setVisibility(View.INVISIBLE);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail().trim();

        } else {
            // No user is signed in
        }
        rating_bar2.setVisibility(View.INVISIBLE);

        if (pre.equals("false")) {
            btnSubmit.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);

        } else {
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
        if (user != null) {
            rateRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshotq) {
                    for (DataSnapshot snapshotc : snapshotq.getChildren()) {
                        rate rateo = snapshotc.getValue(rate.class);
                        if (snapshotc.exists()) {
                            if (rateo.getUserid().equals(user.getUid()) && (rateo.getNoteid().equals(id))) {
                            ratingBar.setVisibility(View.INVISIBLE);
                            flagw=true;
                            }
                        }
                    }//

                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                            s = String.valueOf(ratingBar.getRating());
                            Toast.makeText(getApplicationContext(), "note rated successfully", Toast.LENGTH_SHORT).show();

                            if (flag == true) {
                                currentRate = Float.parseFloat(s);
                                String cn = String.valueOf(currentRate);
//                float c = currentRate + rate;
                                ratenum = ratenum + currentRate;
                                counter = (float) rateCount + 1;
                                newrate = (ratenum / counter);
                                notesRef.child(id).child("allrates").setValue(ratenum);
                                notesRef.child(id).child("rate").setValue(newrate);
                                notesRef.child(id).child("ratingCount").setValue(counter);

                                String id4 = rateRef.push().getKey();
                                rateObj.setId(id4);
                                rateObj.setNoteid(id);
                                rateObj.setUserid(user.getUid());
                                rateObj.setRate1(cn);
                                rateRef.child(id4).setValue(rateObj);
                            }

                        }
                    });

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            rateRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshotq) {
                    for (DataSnapshot snapshotc : snapshotq.getChildren()) {
                        rate rateo = snapshotc.getValue(rate.class);
                        if (snapshotc.exists()) {
                            if (rateo.getUserid().equals(user.getUid()) && (rateo.getNoteid().equals(id))) {
                                ratingBar.setVisibility(View.INVISIBLE);

                                rating_bar2.setVisibility(View.VISIBLE);
                                rateN = rateo.getRate1();
                                final float raten = Float.parseFloat(rateN);
                                rating_bar2.setRating(raten);
                                notepr2.setText("written by : " + username + "\n\nyour Submitted rate");
                                flagw = true;
                            }
                        }
                    }//
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });


        }





        comments = (ListView) findViewById(R.id.commentstt);
        final ArrayList<comment> commentsList = new ArrayList<>();
        commentAdapter = new BrowseCommentAdapter(this, R.layout.adapter_view_layout, commentsList);
        comments.setAdapter(commentAdapter);
        commentsRef = FirebaseDatabase.getInstance().getReference().child("Comments");
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotq) {
                commentsList.clear();
                for (DataSnapshot snapshotc : snapshotq.getChildren()) {
                    comment commentObj = snapshotc.getValue(comment.class);
                    if (commentObj.getNoteID().equals(id)) {
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
                if (user != null) {
                    scomment = newComment.getText().toString().trim();
                    String ucid = user.getEmail().trim();
                    String commID = commentsRef.push().getKey();
                    comment c = new comment(commID, id, scomment, ucid);
                        if(newComment.getText().toString().trim().isEmpty()) {
                        Toast.makeText(previewnote.this, "Comment can not be empty!", Toast.LENGTH_LONG).show();
                        return;
                    } else if(newComment.getText().toString().trim().length()>100){
                        Toast.makeText(previewnote.this, "Comment can not be more than 100 character!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    addNewComment(id, c);
                } else {

                    new AlertDialog.Builder(previewnote.this)
                            .setTitle("you need to login first")
                            .setMessage("Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(previewnote.this, MainActivity.class));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });


    }

    public void addNewComment(String noteID, comment c) {
        commentsRef.child(c.getCommID().trim()).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(previewnote.this, "Your comment added successfully", Toast.LENGTH_LONG).show();
                    newComment.setText("");
                }
            }
        });
    }


}