package com.postit.postit_.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.postit.postit_.Objects.comment;
import com.postit.postit_.Objects.rate;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;
import com.postit.postit_.Objects.note;
import com.postit.postit_.Objects.favoriteList;

import java.util.List;

public class BrowseNoteAdapter extends RecyclerView.Adapter<BrowseNoteAdapter.ViewHolder> {

    Context context;
    private List<note> noteList;
    CustomItemClickListener listener;
    private DatabaseReference noteRef, favouriteRef,commentsRef,ratesRef;
    private FirebaseUser user;
    String userEmail;
    boolean flag = false;
    boolean flag1 = false;
    int rateCount = 0;
    String m;
    float rate;
    note s;

    public BrowseNoteAdapter(Context context, List<note> noteList, CustomItemClickListener listener) {
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
                listener.OnItemClick(view, mv.getAdapterPosition());
            }
        });
        return mv;
    }

    @Override
    public void onBindViewHolder(@NonNull final BrowseNoteAdapter.ViewHolder holder, int position) {
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        commentsRef = FirebaseDatabase.getInstance().getReference().child("Comments");
        favouriteRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");
        ratesRef = FirebaseDatabase.getInstance().getReference().child("Rates");

        final String id = noteList.get(position).getId();
        final String title = noteList.get(position).getTitle();
        final String body = noteList.get(position).getCaption();

        holder.noteTitleD.setText(noteList.get(position).getTitle());
        holder.notedate.setText(noteList.get(position).getDate());

        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    note noteObj = noteSnapshot.getValue(note.class);
                    String x = noteObj.getId();
                    if (x.equals(id)) {
                        rate = noteObj.getRate();
                        holder.ratingBar.setRating(rate);
                        break;

                    }
                    else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.deleten2.setVisibility(View.INVISIBLE);
       holder.remove.setVisibility(View.INVISIBLE);
        holder.addFavourite.setVisibility(View.INVISIBLE);


        holder.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                Uri uri = Uri
                        .parse("android.resource://com.postit.postit_/drawable/rerebg");
                myIntent.setType("text/plain");
                String shareBody = title +":" +"\n" + body + "\n Shared from POST-it.";
                String name = title;
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


        if (noteList.get(position).getEmail().trim().equals(userEmail)) {
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
                                                    favouriteRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshota) {
                                                            for (DataSnapshot childnn8 : snapshota.getChildren()) {
                                                                favoriteList findnote3 = childnn8.getValue(favoriteList.class);
                                                                final String noteid2 = findnote3.getNid();
                                                                final String noteid23 = findnote3.getId();

                                                                if (noteid2.equals(id)) {
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
                                                    commentsRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshota) {
                                                            for (DataSnapshot childnn8 : snapshota.getChildren()) {
                                                                comment findnote34 = childnn8.getValue(comment.class);
                                                                final String noteid24 = findnote34.getNoteID();
                                                                final String noteid25 = findnote34.getCommID();

                                                                if (noteid24.equals(id)) {
                                                                    deleteNote3(noteid25);
                                                                }

                                                            }
                                                        }

                                                        public void deleteNote3(String noteKey) {
                                                            commentsRef.child(noteKey.trim()).removeValue();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                    ratesRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshota) {
                                                            for (DataSnapshot childnn8 : snapshota.getChildren()) {
                                                                rate findnote38 = childnn8.getValue(rate.class);
                                                                final String noteid27 = findnote38.getNoteid();
                                                                final String noteid28 = findnote38.getId();

                                                                if (noteid27.equals(id)) {
                                                                    deleteNote6(noteid28);
                                                                }

                                                            }
                                                        }

                                                        public void deleteNote6(String noteKey) {
                                                            ratesRef.child(noteKey.trim()).removeValue();
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
        if (user != null) {
            final String currentUserid = user.getUid().trim();
            favouriteRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshotiooo) {
                    for (DataSnapshot messageSnapshotii : snapshotiooo.getChildren()) {
                        favoriteList Nobj = messageSnapshotii.getValue(favoriteList.class);
                        if ((Nobj.getNid().equals(id)) && (Nobj.getUserID().equals(currentUserid))){
                            flag1 = true;
                            break;
                        }
                        else flag1 =false;
                    }

                    if (flag1 == true) {
                        holder.remove.setVisibility(View.VISIBLE);
                        holder.addFavourite.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        holder.remove.setVisibility(View.INVISIBLE);
                        holder.addFavourite.setVisibility(View.VISIBLE);
                        holder.addFavourite.setOnClickListener(new View.OnClickListener() {
                            final String currentUserid = user.getUid().trim();

                            @Override
                            public void onClick(View view) {
                                flag = false;
                                noteRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshothh) {
                                        for (DataSnapshot childnn : snapshothh.getChildren()) {
                                            final note findnote = childnn.getValue(note.class);
                                            final String noteid = findnote.getId();
                                            if (noteid.equals(id)) {
                                                s = findnote;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                                new AlertDialog.Builder(context)
                                        .setMessage("do you want to add this note to your bookmarks?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                addToFavoriteList(s);
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();

                            }
                            public void addToFavoriteList(final note findnote) {
                                final favoriteList favNote = new favoriteList();
                                final String id2 = favouriteRef.push().getKey();

                                favNote.setCaption(findnote.getCaption());
                                favNote.setTitle(findnote.getTitle());
                                favNote.setChapterNum(findnote.getChapterNum());
                                favNote.setCollege(findnote.getCollege());
                                favNote.setCourse(findnote.getCourse());
                                favNote.setMajor(findnote.getMajor());
                                favNote.setDate(findnote.getDate());
                                favNote.setEmail(findnote.getEmail());
                                favNote.setId(id2);
                                favNote.setNid(findnote.getId());
                                favNote.setUserID(currentUserid);
                                favNote.setRate(0);
                                favNote.setRatingCount(0);
                                favNote.setAllrates(0);
//                            noteRef.child(findnote.getId()).child("color").setValue(1);
                                favouriteRef.child(id2).setValue(favNote);
                            }
                        });//de
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitleD;
        TextView notedate;
        ImageButton deleten2;
        String date;
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
//
//            Drawable drawableReview = ratingBar.getProgressDrawable();
//            drawableReview.setColorFilter(Color.parseColor("#ffff8800"),
//                    PorterDuff.Mode.SRC_ATOP);
            //ratingBar.setRating(rate);

        }
    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }


}