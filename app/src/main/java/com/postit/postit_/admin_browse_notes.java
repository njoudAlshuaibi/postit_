package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_browse_notes extends AppCompatActivity {
    private ListView noteslistview;
    private FirebaseDatabase database;
    private DatabaseReference notesRef;
    private DatabaseReference ref;
//    private TextView MajorN;
//    private TextView CourseN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_browse_notes);
        Toolbar tb = findViewById(R.id.toolbar_adminnote);
        setSupportActionBar(tb);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();

        final String MajorN = intent.getStringExtra(PopUpWindowAdmin.EXTRA_TEXT);
        final String CourseN = intent.getStringExtra(PopUpWindowAdmin.EXTRA_TEXT2);
        final String chapterN = intent.getStringExtra(PopUpWindowAdmin.EXTRA_TEXT3);


//        v= (TextView) findViewById(R.id.tv);
//        v.setText(text+t);




        noteslistview = findViewById(R.id.listviewadmin);
        final ArrayList<String> notesList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitem, notesList);
        noteslistview.setAdapter(adapter);
        ref = FirebaseDatabase.getInstance().getReference();
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotq) {
                notesList.clear();
                for (DataSnapshot snapshotc : snapshotq.getChildren()) {
                    note noteObj = snapshotc.getValue(note.class);
                    if(MajorN.equalsIgnoreCase(noteObj.getMajor()))
                    {
                        if(CourseN.equalsIgnoreCase(noteObj.getCourse())){
                            if(chapterN.equalsIgnoreCase(noteObj.getChapterNum())){
                    String notedisplay = "collage: " + noteObj.getCollege() + "\nmajor: " + noteObj.getMajor() + "\ncourse: " + noteObj.getCourse() + "\nchapter: " + noteObj.getChapterNum() + "\ntitle: " + noteObj.getTitle() + "\ncaption: " + noteObj.getCaption() + "\nemail: " + noteObj.getEmail()+ "\nID: " +noteObj.getId();
                    notesList.add(notedisplay);}}
                }}
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        noteslistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int whichItem = position;
                String hh = parent.getItemAtPosition(position).toString();
                final String notetKey = hh.substring(hh.indexOf("ID:") + 3, hh.length());
                new AlertDialog.Builder(admin_browse_notes.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notesRef.child(notetKey.trim()).removeValue();
                                notesList.remove(whichItem);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                return true;
            }
        });


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
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(admin_browse_notes.this, MainActivity.class));
//                    finish();
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

        }
        else if (id==R.id.home){
            startActivity(new Intent(admin_browse_notes.this,mainAdmin.class));
        }
        return true;


    }

}