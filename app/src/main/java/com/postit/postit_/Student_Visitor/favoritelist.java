package com.postit.postit_.Student_Visitor;

import androidx.annotation.NonNull;
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
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Adapter.BrowseNoteAdapter;
import com.postit.postit_.Adapter.FavoriteListAdapter;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.note;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class favoritelist extends AppCompatActivity {
    List<note> noteList = new ArrayList<>();
    FavoriteListAdapter noteAdapter ;
    RecyclerView recyclerView;
    private DatabaseReference favouriteRef;
    public static final String preTitel = "com.postit.postit_.preTitel";
    public static final String preCaption = "com.postit.postit_.preCaption";
    public static final String preEmail = "com.postit.postit_.preEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritelist);
        Toolbar toolb = findViewById(R.id.toolbar_favoritelist);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        favouriteRef= FirebaseDatabase.getInstance().getReference().child("FavoriteList");
        favouriteRef.keepSynced(true);

        noteAdapter = new FavoriteListAdapter(this, noteList, new CustomItemClickListener() {
            @Override
            public void OnItemClick(View v, int pos) {

                note n = noteList.get(pos);
                String s = n.getTitle();
                String a = n.getCaption();
                String m = n.getEmail();

                Intent in = new Intent(favoritelist.this, previewnote.class);
                in.putExtra(preTitel,""+s );
                in.putExtra(preCaption,""+a );
                in.putExtra(preEmail,""+m );
                startActivity(in);
            } // end on item click listener
        });
        recyclerView = findViewById(R.id.FavoriteListRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(noteAdapter);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        super.onResume();
        favouriteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){


                    for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                        String favlistid=messageSnapshot.getKey();
                        if (favlistid.equals(user.getUid())){
                            favouriteRef.child(favlistid.trim()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshotiooo) {
                                    noteList.clear();
                                    noteAdapter.notifyDataSetChanged();
                                    for (DataSnapshot messageSnapshotii: snapshotiooo.getChildren()) {
                                        note Nobj = messageSnapshotii.getValue(note.class);
                                        noteList.add(Nobj);}
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.favoritelist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(favoritelist.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(favoritelist.this, MainActivity.class));
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
            startActivity(new Intent(favoritelist.this, StudentActivity.class));
        }
        return true;
    }

}