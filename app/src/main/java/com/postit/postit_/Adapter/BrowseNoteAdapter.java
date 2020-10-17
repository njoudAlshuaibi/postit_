package com.postit.postit_.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.R;
import com.postit.postit_.Student_Visitor.ExplorerNote;
import com.postit.postit_.Student_Visitor.StudentActivity;
import com.postit.postit_.helper.CustomItemClickListener;
import com.postit.postit_.Objects.note;
import java.util.List;

public class BrowseNoteAdapter  extends RecyclerView.Adapter <BrowseNoteAdapter.ViewHolder> {

    Context context;
    private List<note> noteList;
    CustomItemClickListener listener;
    private DatabaseReference noteRef;
    private FirebaseUser user;
    String userEmail;


    public BrowseNoteAdapter(Context context, List<note> noteList , CustomItemClickListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BrowseNoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notedesign, viewGroup, false);

        final ViewHolder mv = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(view , mv.getAdapterPosition());
            }
        });
        return  mv;
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseNoteAdapter.ViewHolder holder, int position) {
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        final String id = noteList.get(position).getId();
        //   holder.notedate.setText("\n\n\n\n\n                   "+"                 "+date);
        holder.noteTitleD.setText(noteList.get(position).getTitle());
        Log.d("===", noteList.get(position).getTitle() + " 0");
        holder.notedate.setText(noteList.get(position).getDate());
        holder.deleten2.setVisibility(View.INVISIBLE);


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail().trim();
        } else {
            // No user is signed in
        }


        if (noteList.get(position).getEmail().trim().equals(userEmail))
     {
            holder.deleten2.setVisibility(View.VISIBLE);
        holder.deleten2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshothh) {
                        for (DataSnapshot childnn : snapshothh.getChildren()) {
                            note findnote = childnn.getValue(note.class);
                            final String noteid = findnote.getId();


                            if (noteid.equals(id)) {
                                AlertDialog alertDialog = new AlertDialog.Builder(context)
                                        .setTitle("are you sure?")
                                        .setMessage("do you want to delete this note? ")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteNote(noteid);
                                                                                          }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();

                            }

                        }
                    }

                    public void deleteNote(String noteKey) {
                        noteRef.child(noteKey.trim()).removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitleD ;
        TextView notedate;
        ImageButton deleten2;
        String date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleD = itemView.findViewById(R.id.noteTitle);
            notedate = itemView.findViewById(R.id.notedate);
            deleten2 = itemView.findViewById(R.id.deleten2);

        }
    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }
}