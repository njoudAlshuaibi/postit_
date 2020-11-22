package com.postit.postit_.Student_Visitor;

import androidx.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Adapter.FavoriteListAdapter;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.favoriteList;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class favoritelist extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout2;
    NavigationView navigationView2;
    Toolbar toolbar2;
    List<favoriteList> noteList = new ArrayList<>();
    FavoriteListAdapter noteAdapter ;
    RecyclerView recyclerView;
    private DatabaseReference favouriteRef;
    public static final String preTitel = "com.postit.postit_.preTitel";
    public static final String preCaption = "com.postit.postit_.preCaption";
    public static final String preEmail = "com.postit.postit_.preEmail";
    public static final String preID = "com.postit.postit_.preID";
    public static final String prerate = "com.postit.postit_.prerate";
    public static final String precrate = "com.postit.postit_.precrate";
    public static final String precratenum = "com.postit.postit_.precratenum";
    public static final String precc = "com.postit.postit_.precc";
    private TextView textView;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private TextView welcome;
    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritelist);
        drawerLayout2=findViewById(R.id.drawer_layout2);
        navigationView2=findViewById(R.id.nav_view2);
        toolbar2=findViewById(R.id.toolbar_favoritelist);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("BOOKMARKS");
        toolbar2.setTitleTextColor(0xFF000000);
        navigationView2.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout2,toolbar2,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout2.addDrawerListener(toggle);
        toggle.syncState();

        navigationView2.setNavigationItemSelectedListener(this);
        Menu menu= navigationView2.getMenu();

            menu.findItem(R.id.nav_login).setVisible(false);

        favouriteRef= FirebaseDatabase.getInstance().getReference().child("FavoriteList");
        favouriteRef.keepSynced(true);

        textView = (TextView) findViewById(R.id.tv23);

        noteAdapter = new FavoriteListAdapter(this, noteList, new CustomItemClickListener() {
            @Override
            public void OnItemClick(View v, int pos) {

                favoriteList n = noteList.get(pos);
                String s = n.getTitle();
                String a = n.getCaption();
                String m = n.getEmail();
                String i = n.getId();
                float r = n.getRate();
                String ca =String.valueOf(r);
                int ratec = n.getRatingCount();
                String raco =String.valueOf(ratec);
                float ra = n.getAllrates();
                String cae =String.valueOf(ra);
                String nid = n.getNid();

                Intent in = new Intent(favoritelist.this, previewnote.class);
                in.putExtra(preTitel,""+s );
                in.putExtra(preCaption,""+a );
                in.putExtra(preEmail,""+m );
                in.putExtra(preID,nid);
                in.putExtra(prerate,ca);
                in.putExtra(precrate,raco);
                in.putExtra(precratenum,cae);
                in.putExtra(precc,"false");


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
            public void onDataChange(@NonNull DataSnapshot snapshotiooo) {
                noteList.clear();
                noteAdapter.notifyDataSetChanged();
                for (DataSnapshot messageSnapshotii: snapshotiooo.getChildren()) {
                    favoriteList Nobj = messageSnapshotii.getValue(favoriteList.class);
                    if(Nobj.getUserID().equals(user.getUid()))
                        noteList.add(Nobj);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        noteAdapter.notifyDataSetChanged();

        favouriteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotrf) {
                for (DataSnapshot childrf6 : snapshotrf.getChildren()) {
                    favoriteList findNote = childrf6.getValue(favoriteList.class);
                    String noteOwner =findNote.getUserID();
                    if (noteOwner.equals(user.getUid())) {
                        textView.setText("");
                        break;
                    }

                    else {
                        textView.setText("Bookmarks empty");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @Override
    public void onBackPressed(){
        if(drawerLayout2.isDrawerOpen(GravityCompat.START)){

            drawerLayout2.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
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
                Intent intent1= new Intent(favoritelist.this,StudentActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2= new Intent(favoritelist.this,Profile.class);
                startActivity(intent2);
                break;
            case R.id.nav_chat:
                Intent intent3= new Intent(favoritelist.this,usersChats.class);
                startActivity(intent3);
                break;

            case R.id.nav_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(favoritelist.this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);

                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                    // signOut
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(favoritelist.this, MainActivity.class));

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
        return true;}
}