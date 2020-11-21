package com.postit.postit_.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Objects.comment;
import com.postit.postit_.R;

import java.util.ArrayList;

public class BrowseCommentAdapter extends ArrayAdapter<comment> {
    Context mcontext;
    int mResource;
    private FirebaseUser user;
    String currentUserid;
    private DatabaseReference commentsRef;
    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");
    String username;

    public BrowseCommentAdapter(Context context, int resource, ArrayList<comment> obj) {
        super(context, resource, obj);
        mcontext = context;
        mResource = resource;

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            currentUserid = user.getEmail().trim();
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String comm = getItem(position).getComm();
        final String commID = getItem(position).getCommID();
        final String notID = getItem(position).getNoteID();
        String comR = getItem(position).getComR();
        commentsRef = FirebaseDatabase.getInstance().getReference().child("Comments");

        comment comment = new comment(commID, notID, comm, comR);
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageButton deletenc = (ImageButton) convertView.findViewById(R.id.deletenc9);
        deletenc.setVisibility(View.INVISIBLE);
        if (user != null) {
            if (currentUserid.equals(comR)) {
                deletenc.setVisibility(View.VISIBLE);
                deletenc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commentsRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshothh) {
                                for (DataSnapshot messageSnapshoti : snapshothh.getChildren()) {
                                    comment cobj = messageSnapshoti.getValue(comment.class);
                                    final String cobjId = cobj.getCommID();
                                    if (cobj.getComR().equals(currentUserid)) {
                                        if (cobj.getCommID().equals(commID)) {
                                            AlertDialog alertDialog = new AlertDialog.Builder(mcontext)
                                                    .setTitle("are you sure?")
                                                    .setMessage("do you want to delete this comment? ")
                                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            deleteNote(cobjId);
                                                        }
                                                    })
                                                    .setNegativeButton("No", null)
                                                    .show();
                                        }
                                    }
                                }
                            }

                            public void deleteNote(String noteKey) {
                                commentsRef.child(noteKey.trim()).removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                });
            }///

        }
        TextView tvcomm = (TextView) convertView.findViewById(R.id.commentET);
        tvcomm.setText(comm);
        TextView comr = (TextView) convertView.findViewById(R.id.commentr);

        g.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    com.postit.postit_.Objects.user usa = postSnapshot.getValue(com.postit.postit_.Objects.user.class);
                    if(usa.getEmail().equals(comR)){
                        username = usa.getUsername();
                        comr.setText("By: " + username);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        return convertView;
    }


}

