package com.postit.postit_.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.postit.postit_.Objects.favoriteList;
import com.postit.postit_.R;
import com.postit.postit_.Student_Visitor.ExplorerNote;
import com.postit.postit_.Student_Visitor.StudentActivity;
import com.postit.postit_.Student_Visitor.favoritelist;
import com.postit.postit_.helper.CustomItemClickListener;
import com.postit.postit_.Objects.note;
import java.util.List;


public class FavoriteListAdapter  extends RecyclerView.Adapter <FavoriteListAdapter.ViewHolder> {
    Context context;
    private List<favoriteList> noteList;
    CustomItemClickListener listener;
    private DatabaseReference favouriteRef;
    private DatabaseReference noteRef;
    String userEmail;
    private FirebaseUser user;
    float rate;





    public FavoriteListAdapter(Context context, List<favoriteList> noteList , CustomItemClickListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FavoriteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notedesign, viewGroup, false);

        final FavoriteListAdapter.ViewHolder mv = new FavoriteListAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(view , mv.getAdapterPosition());
            }
        });
        return  mv;


    }
    @Override
    public int getItemCount() {
        return noteList.size();
    }
    @Override
    public void onBindViewHolder(@NonNull final FavoriteListAdapter.ViewHolder holder, int position) {
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        favouriteRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");
        holder.noteTitleD.setText(noteList.get(position).getTitle());
        holder.notedate.setText(noteList.get(position).getDate());
        holder.deleten2.setVisibility(View.INVISIBLE);
        final String id5 = noteList.get(position).getNid();
        final String title = noteList.get(position).getTitle();
        final String body = noteList.get(position).getCaption();
        holder.addFavourite.setVisibility(View.INVISIBLE);

        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    note noteObj = noteSnapshot.getValue(note.class);
                    String x = noteObj.getId();
                    if (x.equals(id5)) {
                        rate = noteObj.getRate();
                        break;
                    }else{

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        favouriteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    favoriteList noteObj = noteSnapshot.getValue(favoriteList.class);
                    String x = noteObj.getNid();
                    if (x.equals(id5)) {
                        holder.ratingBar.setRating(rate);

                        break;
                    }else{

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                Uri uri = Uri
                        .parse("android.resource://com.postit.postit_/drawable/logo");
                myIntent.setType("text/plain");
                String shareBody = " title: " + title +"\n caption: "+body+"\n Shared from POST-it.";
                String name=title;
                myIntent.putExtra(Intent.EXTRA_SUBJECT, name);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                myIntent.putExtra(Intent.EXTRA_STREAM, uri);
                myIntent.setType("image/png");
                myIntent.setPackage("com.twitter.android");
                context.startActivity(Intent.createChooser(myIntent, "Share this via"));


            }
        });
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
                        public void onDataChange(@NonNull DataSnapshot snapshothhh) {
                            for (DataSnapshot childnn4 : snapshothhh.getChildren()) {
                                note findnote = childnn4.getValue(note.class);
                                final String noteid = findnote.getId();

                                if (noteid.equals(id5)) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                                            .setTitle("are you sure?")
                                            .setMessage("do you want to delete this note? ")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    deleteNote(noteid);
                                                    favouriteRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshota) {
                                                            for (DataSnapshot childnn8 : snapshota.getChildren()) {
                                                                favoriteList findnote3 = childnn8.getValue(favoriteList.class);
                                                                final String noteid2 = findnote3.getNid();
                                                                final String noteid23 = findnote3.getId();


                                                                if (noteid2.equals(id5)) {
                                                                    deleteNote2(noteid23);
                                                                }

                                                            }
                                                        }
                                                        public void deleteNote2(String noteKey) {
                                                            favouriteRef.child(noteKey.trim()).removeValue();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
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

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getEmail().equals(userEmail)) {
            holder.remove.setVisibility(View.VISIBLE);
            holder.remove.setOnClickListener(new View.OnClickListener() {
                final String currentUserid = user.getUid().trim();

                @Override
                public void onClick(View view) {
                    favouriteRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshothh) {
                            for (DataSnapshot messageSnapshoti : snapshothh.getChildren()) {
                                favoriteList Nobj = messageSnapshoti.getValue(favoriteList.class);
                                final String Nobjid = Nobj.getId();
                                if(Nobj.getUserID().equals(currentUserid)){
                                    if (Nobj.getNid().equals(id5)) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                                .setTitle("are you sure?")
                                                .setMessage("do you want to remove this note from favorite list? ")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        deleteNote(Nobjid);
                                                    }
                                                })
                                                .setNegativeButton("No", null)
                                                .show();
                                    }}
                            }


                        }

                        public void deleteNote(String noteKey) {
                            favouriteRef.child(noteKey.trim()).removeValue();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            });
        }

        favouriteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshoti) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitleD ;
        TextView notedate;
        ImageButton deleten2;
        ImageView imageView3;
        ImageButton addFavourite;
        ImageButton remove;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleD = itemView.findViewById(R.id.noteTitle);
            notedate = itemView.findViewById(R.id.notedate);
            deleten2 = itemView.findViewById(R.id.deleten2);
            imageView3 = itemView.findViewById(R.id.imageView3);
            addFavourite = itemView.findViewById(R.id.fvrt_f2_item);
            remove = itemView.findViewById(R.id.remove);
            ratingBar = itemView.findViewById(R.id.rating_bar);



        }
    }

}