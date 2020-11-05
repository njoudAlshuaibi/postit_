package com.postit.postit_.Student_Visitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

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
import com.postit.postit_.Objects.note;
import com.postit.postit_.Objects.user;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;
import com.postit.postit_.Objects.user;

import java.util.ArrayList;
import java.util.List;

public class mynotes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout3;
    NavigationView navigationView6;
    Toolbar toolbar3;
    private FloatingActionButton fab;
    private DatabaseReference noteRef, notesRef, usersRef;
    List<note> noteList = new ArrayList<>();
    BrowseNoteAdapter noteAdapter;
    RecyclerView recyclerView;
    private FirebaseUser user;
    private String userEmail;
    private TextView textView;
    String noteWriterUserID = "hh99";
    public static final String preTitel = "com.postit.postit_.preTitel";
    public static final String preCaption = "com.postit.postit_.preCaption";
    public static final String preEmail = "com.postit.postit_.preEmail";
    public static final String preID = "com.postit.postit_.preID";
    public static final String prerate = "com.postit.postit_.prerate";
    public static final String precrate = "com.postit.postit_.precrate";
    public static final String precratenum = "com.postit.postit_.precratenum";
    public static final String precc = "com.postit.postit_.precc";
    public static final String noteWriterID = "com.postit.postit_.noteWriterID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynotes);
        drawerLayout3 = findViewById(R.id.drawer_layout3);
        navigationView6 = findViewById(R.id.nav_view3);
        toolbar3 = findViewById(R.id.toolbar_mynotes);
        setSupportActionBar(toolbar3);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My notes");
        toolbar3.setTitleTextColor(0xFFB8B8B8);
        navigationView6.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout3, toolbar3, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout3.addDrawerListener(toggle);
        toggle.syncState();

        navigationView6.setNavigationItemSelectedListener(this);
        Menu menu = navigationView6.getMenu();

        menu.findItem(R.id.nav_login).setVisible(false);
        textView = (TextView) findViewById(R.id.tvnotes);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencreatnotepopup();
            }
        });
        userEmail = "";
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail().trim();
        } else {
            // No user is signed in
        }
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef = FirebaseDatabase.getInstance().getReference().child("Notes");
        noteRef.keepSynced(true);
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

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


                Intent in = new Intent(mynotes.this, previewnote.class);
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
        recyclerView = findViewById(R.id.MynoteRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(noteAdapter);

        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    noteList.clear();
                    noteAdapter.notifyDataSetChanged();

                    for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                        note a = messageSnapshot.getValue(note.class);

                        if (a.getEmail().trim().equals(userEmail))
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
                    String EmailN = findNote.getEmail();
                    if (EmailN.equals(userEmail)) {
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

        // juju delete mynote start


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout3.isDrawerOpen(GravityCompat.START)) {
            drawerLayout3.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void opencreatnotepopup() {
        Intent popupwindow4 = new Intent(mynotes.this, creatnotepopup.class);
        startActivity(popupwindow4);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1 = new Intent(mynotes.this, StudentActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent(mynotes.this, Profile.class);
                startActivity(intent2);
                break;
            case R.id.nav_chat:
                Intent intent3 = new Intent(mynotes.this, chatActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_notification:
                break;
            case R.id.nav_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(mynotes.this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);

                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                    // signOut
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(mynotes.this, MainActivity.class));

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
                Intent intent4 = new Intent(mynotes.this, MainActivity.class);
                startActivity(intent4);

        }
        return true;
    }

}