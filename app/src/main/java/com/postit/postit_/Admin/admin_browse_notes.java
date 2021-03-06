package com.postit.postit_.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.favoriteList;
import com.postit.postit_.Objects.note;
import com.postit.postit_.Objects.requests;
import com.postit.postit_.R;

import java.util.ArrayList;

public class admin_browse_notes extends AppCompatActivity {

    private ListView noteslistview;
    private TextView textView, textview10, currentCandChAdmin;
    private DatabaseReference notesRef;
    private DatabaseReference ref;
    private DatabaseReference noteRef;
    DatabaseReference favouriteRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        favouriteRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_browse_notes);
        Toolbar toolbar = findViewById(R.id.toolbar_adminnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Browse notes");
        toolbar.setTitleTextColor(0xFF000000);

        Intent intent = getIntent();
        textView = (TextView) findViewById(R.id.tp);
        textview10 = (TextView) findViewById(R.id.textview10);
        currentCandChAdmin = (TextView) findViewById(R.id.currentCandChAdmin);

        final String MajorN = intent.getStringExtra(PopUpWindowAdmin.adminMajorChoicee);
        final String CourseN = intent.getStringExtra(PopUpWindowAdmin.adminCourceChoicee);
        final String chapterN = intent.getStringExtra(PopUpWindowAdmin.adminChapterChoicee);

        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");


        noteslistview = findViewById(R.id.listviewadmin);
        final ArrayList<note> notesList = new ArrayList<>();
        final admin_browse_notes.MyCustomAdapter myadpter = new admin_browse_notes.MyCustomAdapter(notesList);
        noteslistview.setAdapter(myadpter);
        ref = FirebaseDatabase.getInstance().getReference();
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotq) {
                notesList.clear();
                for (DataSnapshot snapshotc : snapshotq.getChildren()) {
                    note noteObj = snapshotc.getValue(note.class);
                    if (MajorN.equalsIgnoreCase(noteObj.getMajor())) {
                        if (CourseN.equalsIgnoreCase(noteObj.getCourse())) {
                            if (chapterN.equalsIgnoreCase(noteObj.getChapterNum())) {
                                String notedisplay = "collage: " + noteObj.getCollege() + "\nmajor: " + noteObj.getMajor() + "\ncourse: " + noteObj.getCourse() + "\nchapter: " + noteObj.getChapterNum() + "\ntitle: " + noteObj.getTitle() + "\ncaption: " + noteObj.getCaption() + "\nemail: " + noteObj.getEmail() + "\nRate: " + noteObj.getRate() + " Out of 4" + "\nID: " + noteObj.getId();
                                notesList.add(noteObj);
                            }
                        }
                    }
                }
                myadpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotrf) {
                for (DataSnapshot childrf : snapshotrf.getChildren()) {
                    note findNote = childrf.getValue(note.class);
                    String courseId = findNote.getCourse();
                    String chapterId = findNote.getChapterNum();
                    if (courseId.equals(CourseN) && chapterId.equals(chapterN)) {
                        textView.setText("");
                        currentCandChAdmin.setText(CourseN + " - " + chapterN);
                        break;
                    } else {
                        textView.setText("no existing notes");
                        textview10.setText("");


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        noteslistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final int whichItem = position;
//                String hh = parent.getItemAtPosition(position).toString();
//                final String notetKey = hh.substring(hh.indexOf("ID:") + 3, hh.length());
//                new AlertDialog.Builder(admin_browse_notes.this)
//                        .setIcon(android.R.drawable.ic_delete)
//                        .setTitle("Are you sure?")
//                        .setMessage("Do you want to delete this note")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                notesRef.child(notetKey.trim()).removeValue();
//                                favouriteRef.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshota) {
//                                        for (DataSnapshot childnn8 : snapshota.getChildren()) {
//                                            favoriteList findnote3 = childnn8.getValue(favoriteList.class);
//                                            final String noteid2 = findnote3.getNid();
//                                            final String noteid23 = findnote3.getId();
//
//
//                                            if (noteid2.equals(notetKey.trim())) {
//                                                deleteNote2(noteid23);
//                                            }
//
//                                        }
//                                    }
//
//                                    public void deleteNote2(String noteKey) {
//                                        favouriteRef.child(noteKey.trim()).removeValue();
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//                                notesList.remove(whichItem);
//                                adapter.notifyDataSetChanged();
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
//
//
//                return true;
//            }
//        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(admin_browse_notes.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(admin_browse_notes.this, MainActivity.class));

                }
            });


            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (id == R.id.home) {
            startActivity(new Intent(admin_browse_notes.this, mainAdmin.class));
        }
        return true;
    }
    class MyCustomAdapter extends BaseAdapter {
        ArrayList<note> Items = new ArrayList<note>();

        MyCustomAdapter(ArrayList<note> Items) {
            this.Items = Items;

        }


        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public String getItem(int position) {
            return Items.get(position).getId();

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.note_item, null);

            TextView txtname = (TextView) view1.findViewById(R.id.noteAdmin);
            txtname.setText("Major: " + Items.get(i).getMajor() + "\nCourse: " + Items.get(i).getCourse() + "\nChapter: " + Items.get(i).getChapterNum()+ "\nNote title: " + Items.get(i).getTitle()+ "\nNote: " + Items.get(i).getCaption()+ "\nRate: " + Items.get(i).getRate());
            ImageView deleteNote = (ImageView) view1.findViewById(R.id.deleteNote);
            deleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String m = Items.get(i).getId();



                    new AlertDialog.Builder(admin_browse_notes.this)
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Are you sure?")
                            .setMessage("Do you want to delete this note")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    notesRef.child(m.trim()).removeValue();
                                    favouriteRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshota) {
                                            for (DataSnapshot childnn8 : snapshota.getChildren()) {
                                                favoriteList findnote3 = childnn8.getValue(favoriteList.class);
                                                final String noteid2 = findnote3.getNid();
                                                final String noteid23 = findnote3.getId();


                                                if (noteid2.equals(m.trim())) {
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

            });
            return view1;

        }
    }
    }
