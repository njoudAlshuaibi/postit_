package com.postit.postit_.Student_Visitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Adapter.BrowseNoteAdapter;
import com.postit.postit_.MainActivity;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;
import com.postit.postit_.helper.Session;
import com.postit.postit_.Objects.note;

import java.util.ArrayList;
import java.util.List;

public class ExplorerNote extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.postit.postit_.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.postit.postit_.EXTRA_TEXT2";
    public static final String EXTRA_TEXT3 = "com.postit.postit_.EXTRA_TEXT3";
    public static final String EXTRA_TEXT7 = "com.postit.postit_.EXTRA_TEXT7";
    public static final String EXTRA_TEXT8 = "com.postit.postit_.EXTRA_TEXT8";
    public static final String EXTRA_TEXT9 = "com.postit.postit_.EXTRA_TEXT9";

    String m;
    String c;
    String d;

    List<note> noteList = new ArrayList<>();
    BrowseNoteAdapter noteAdapter ;
    RecyclerView recyclerView;
    private DatabaseReference noteRef;
    private FloatingActionButton button;
    Session session;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_note);
        Toolbar tool = findViewById(R.id.toolbar_Browsenotes);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        session = new Session(getApplicationContext());

        Intent intent = getIntent();
        m = intent.getStringExtra(popUpWindow.EXTRA_TEXT4);
        c = intent.getStringExtra(popUpWindow.EXTRA_TEXT5);
        d = intent.getStringExtra(popUpWindow.EXTRA_TEXT6);

        button = findViewById(R.id.ExNotefab2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( user == null )
                {
                    Toast.makeText(getApplicationContext() , "You Have To Log In" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openpopupwindowchapters();
                }

            }
        });

        noteRef= FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef.keepSynced(true);

        noteAdapter = new BrowseNoteAdapter(this, noteList, new CustomItemClickListener() {
            @Override
            public void OnItemClick(View v, int pos) {

                note n = noteList.get(pos);
                String s = n.getDate();
                String a = n.getCaption();

                Intent in = new Intent(ExplorerNote.this, previewnote.class);
                in.putExtra(EXTRA_TEXT,""+s );
                in.putExtra(EXTRA_TEXT2,""+a );
                startActivity(in);
            } // end on item click listener
        });
        recyclerView = findViewById(R.id.ExNotemyRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    noteList.clear();
                    noteAdapter.notifyDataSetChanged();

                    for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                        note a = messageSnapshot.getValue(note.class);
                        Log.d("=====" , a.getCaption());
                        Log.d("=====" , a.getTitle());
                        Log.d("=====" , a.getDate());

                        if (a.getChapterNum().equals(d)&&a.getCourse().equals(c))
                            noteList.add(a);
                    }
                }
                else {
                    Log.d("===" , "No Data Was Found");
                }

                noteAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("==" , "On Pause Now");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("==" , "On Stop Now");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("==" , "On Restart Now");

    }

    private void openpopupwindowchapters() {

        Intent popupwindow5 = new Intent(ExplorerNote.this, popupwindowchapters.class);
        popupwindow5.putExtra(EXTRA_TEXT7, m);
        popupwindow5.putExtra(EXTRA_TEXT8, c);
        popupwindow5.putExtra(EXTRA_TEXT9, d);


        //  when this activity started will have the search data which will be delete when we go to different  activity
        // coz we started a new activity from this one; when we come back the data will be lost
        // start activity for result will save the current activity data


        startActivityForResult(popupwindow5 , RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("==" , "On Destoried Now");

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mynotes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ExplorerNote.this);
            builder.setMessage("Are you Sure you want to exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(ExplorerNote.this, MainActivity.class));
                    finish();
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
            if(currentUser != null){
                startActivity(new Intent(ExplorerNote.this, StudentActivity.class));
            }else {
                new AlertDialog.Builder(ExplorerNote.this)
                        .setTitle("you need to login first")
                        .setMessage("Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent (ExplorerNote.this,MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        }
        return true;
    }
}