package com.postit.postit_.Student_Visitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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

public class ExplorerNote extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout4;
    NavigationView navigationView4;
    Toolbar toolbar4;
    String noteWriterUserID = "jj99";
    public static final String preTitel = "com.postit.postit_.preTitel";
    public static final String preCaption = "com.postit.postit_.preCaption";
    public static final String preEmail = "com.postit.postit_.preEmail";
    public static final String preID = "com.postit.postit_.preID";
    public static final String prerate = "com.postit.postit_.prerate";
    public static final String precrate = "com.postit.postit_.precrate";
    public static final String precc = "com.postit.postit_.precc";
    public static final String precratenum = "com.postit.postit_.precratenum";
    public static final String currentMajor = "com.postit.postit_.currentMajor";
    public static final String currentCourse = "com.postit.postit_.currentCourse";
    public static final String currentChapter = "com.postit.postit_.currentChapter";
    public static final String noteWriterID = "com.postit.postit_.noteWriterID";
    private TextView welcome;
    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference notesRef, usersRef;
    private TextView textView, currentCandCh;
    String m;
    String c;
    String d;


    List<note> noteList = new ArrayList<>();
    BrowseNoteAdapter noteAdapter;
    RecyclerView recyclerView;
    private DatabaseReference noteRef;
    private FloatingActionButton button;
    Session session;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_note);
        drawerLayout4 = findViewById(R.id.drawer_layout4);
        navigationView4 = findViewById(R.id.nav_view4);
        toolbar4 = findViewById(R.id.toolbar_Browsenotes);
        setSupportActionBar(toolbar4);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Browe notes");
        toolbar4.setTitleTextColor(0xFFB8B8B8);
        navigationView4.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout4, toolbar4, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout4.addDrawerListener(toggle);
        toggle.syncState();

        navigationView4.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        session = new Session(getApplicationContext());
        Menu menu = navigationView4.getMenu();
        if (user == null) {
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_chat).setVisible(false);
            menu.findItem(R.id.nav_notification).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
        } else {
            menu.findItem(R.id.nav_login).setVisible(false);
        }
        Intent intent = getIntent();
        m = intent.getStringExtra(popUpWindow.EXTRA_TEXT4);
        c = intent.getStringExtra(popUpWindow.EXTRA_TEXT5);
        d = intent.getStringExtra(popUpWindow.EXTRA_TEXT6);
        currentCandCh = (TextView) findViewById(R.id.currentCandCh);
        currentCandCh.setText(c + " - " + d);
        button = findViewById(R.id.ExNotefab2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "You Have To Log In", Toast.LENGTH_SHORT).show();
                } else {
                    openpopupwindowchapters();
                }

            }
        });
        textView = (TextView) findViewById(R.id.tv);
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        noteRef.keepSynced(true);

        noteAdapter = new BrowseNoteAdapter(this, noteList, new CustomItemClickListener() {
            @Override
            public void OnItemClick(View v, int pos) {

                note n = noteList.get(pos);
                String s = n.getTitle();
                String a = n.getCaption();
                final String m = n.getEmail();
                String i = n.getId();
                float r = n.getRate();
                String ca = String.valueOf(r);
                int ratec = n.getRatingCount();
                String raco = String.valueOf(ratec);
                float ra = n.getAllrates();
                String cae = String.valueOf(ra);


                Intent in = new Intent(ExplorerNote.this, previewnote.class);
                in.putExtra(preTitel, "" + s);
                in.putExtra(preCaption, "" + a);
                in.putExtra(preEmail, "" + m);
                in.putExtra(preID, i);
                in.putExtra(prerate, ca);
                in.putExtra(precrate, raco);
                in.putExtra(precratenum, cae);
                in.putExtra(precc, "true");


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

                if (snapshot.exists()) {
                    noteList.clear();
                    noteAdapter.notifyDataSetChanged();

                    for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                        note a = messageSnapshot.getValue(note.class);
                        if (a.getChapterNum().equals(d) && a.getCourse().equals(c))
                            noteList.add(a);
                    }
                } else {
                    Log.d("===", "No Data Was Found");
                }

                noteAdapter.notifyDataSetChanged();
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
                    if (courseId.equals(c) && chapterId.equals(d)) {
                        textView.setText("");
                        break;
                    } else {
                        textView.setText("no existing notes");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout4.isDrawerOpen(GravityCompat.START)) {

            drawerLayout4.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openpopupwindowchapters() {

        Intent popupwindow5 = new Intent(ExplorerNote.this, popupwindowchapters.class);
        popupwindow5.putExtra(currentMajor, m);
        popupwindow5.putExtra(currentCourse, c);
        popupwindow5.putExtra(currentChapter, d);


        //  when this activity started will have the search data which will be delete when we go to different  activity
        // coz we started a new activity from this one; when we come back the data will be lost
        // start activity for result will save the current activity data


        startActivityForResult(popupwindow5, RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        welcome= (TextView) findViewById(R.id.welcome1);
        if(user!=null){
            g.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        com.postit.postit_.Objects.user us = postSnapshot.getValue(com.postit.postit_.Objects.user.class);
                        if(us.getEmail().equals(user.getEmail())){
                            welcome.setText("Welcome"+" "+us.getUsername());

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });}
        else{
            welcome.setText("     "+"Welcome");
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1 = new Intent(ExplorerNote.this, StudentActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent(ExplorerNote.this, Profile.class);
                startActivity(intent2);
                break;
            case R.id.nav_chat:
                Intent intent3 = new Intent(ExplorerNote.this, chatActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_notification:
                break;
            case R.id.nav_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(ExplorerNote.this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    // signOut
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(ExplorerNote.this, MainActivity.class));
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
                break;
            case R.id.nav_login:
                Intent intent4 = new Intent(ExplorerNote.this, MainActivity.class);
                startActivity(intent4);
        }
        return true;
    }
}