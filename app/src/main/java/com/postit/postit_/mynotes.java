package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class mynotes extends AppCompatActivity {
private FloatingActionButton fab;
private note b;
    private FirebaseDatabase database;
    private DatabaseReference notesRef;
    private DatabaseReference ref;

    private RecyclerView notesRecyclerview;
    private List<note> Notes;
    private notesAdapter NoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynotes);

//    ref = FirebaseDatabase.getInstance().getReference();
//    notesRef = FirebaseDatabase.getInstance().getReference().child("Notes");

//        Toolbar toolba = findViewById(R.id.toolbar_mynotes);
//        setSupportActionBar(toolba);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                opencreatnotepopup();
//            }
//        });



        //RecycleView

//        notesRecyclerview=findViewById(R.id.noteRecycleView);
//        notesRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//
//        Notes=new ArrayList<>();
//        NoteAdapter=new notesAdapter(Notes);
//
//        notesRecyclerview.setAdapter(NoteAdapter);
//        getNote();
//
//    }
//
//    private void getNote() {
//        //@SuppressLint("StaticFieldLeak")
//
//
//
//    }
//
//
//    private void opencreatnotepopup() {
//        Intent popupwindow4 = new Intent(mynotes.this,creatnotepopup.class);
//        startActivity(popupwindow4);
//    }
////heyy
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.mynotes, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.exit) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(mynotes.this);
//            builder.setMessage("Are you Sure you want to exit?");
//            builder.setCancelable(true);
//
//            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
//
//                // signOut
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    FirebaseAuth.getInstance().signOut();
//                    startActivity(new Intent(mynotes.this, MainActivity.class));
////                    finish();
//                }
//            });
//
//
//            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//
//        }
//        else if (id==R.id.home){
//            startActivity(new Intent(mynotes.this,StudentActivity.class));
//        }
//        return true;
    }
}