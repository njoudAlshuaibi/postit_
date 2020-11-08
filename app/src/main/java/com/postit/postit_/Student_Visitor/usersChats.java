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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.user;
import com.postit.postit_.Objects.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.postit.postit_.Adapter.BrowseUserAdapter;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;

public class usersChats extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout66;
    NavigationView navigationView66;
    Toolbar toolbar66;
    public static final String sender = "com.postit.postit_.sender";
    public static final String receiver = "com.postit.postit_.receiver";
    FirebaseUser fuser;
    DatabaseReference Ref, usersRef;
    String fuserID;
    String recevierEQLcurrent = "";
    boolean f = false;
    boolean ifAdded;
    private RecyclerView recyclerView;
    private BrowseUserAdapter adapter;
    private List<user> userList = new ArrayList<>();
    private List<String> chatUsers = new ArrayList<>();
    private TextView welcome;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_chats);
        drawerLayout66 = findViewById(R.id.drawer_layout66);
        navigationView66 = findViewById(R.id.nav_view66);
        toolbar66 = findViewById(R.id.toolbar66);
        setSupportActionBar(toolbar66);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("DIRECT MESSAGES");
        toolbar66.setTitleTextColor(0xFFB8B8B8);
        navigationView66.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout66, toolbar66, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout66.addDrawerListener(toggle);
        toggle.syncState();
        navigationView66.setNavigationItemSelectedListener(this);

        navigationView66.setNavigationItemSelectedListener(this);
        Menu menu= navigationView66.getMenu();

        menu.findItem(R.id.nav_login).setVisible(false);

        recyclerView = findViewById(R.id.usersChatsRV);

        Ref = FirebaseDatabase.getInstance().getReference("Massages");
        Ref.keepSynced(true);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        fuserID = fuser.getUid();


        // find users who start chat

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotiooo) {
                userList.clear();
                chatUsers.clear();
                for (DataSnapshot messageSnapshotii : snapshotiooo.getChildren()) {
                    ifAdded = false;
                    chat chatObj = messageSnapshotii.getValue(chat.class);
                    if (chatObj.getReceiverId().equals(fuserID)) {

                        for (int i = 0; i < chatUsers.size(); i++) {
                            if (chatUsers.get(i).equals(chatObj.getSenderId())) {
                                ifAdded = true;
                                break;
                            }
                        }
                        if (!ifAdded) {
                            chatUsers.add(chatObj.getSenderId());
                        }

                    }
                    if (chatObj.getSenderId().equals(fuserID)) {

                        for (int i = 0; i < chatUsers.size(); i++) {
                            if (chatUsers.get(i).equals(chatObj.getReceiverId())) {
                                ifAdded = true;
                                break;
                            }
                        }
                        if (!ifAdded) {
                            chatUsers.add(chatObj.getReceiverId());
                        }


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //find user object to add it to the list while knowing its ID
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userList.clear();
                for (DataSnapshot dataSnapshotp : snapshot.getChildren()) {
                    user user1 = dataSnapshotp.getValue(user.class);
                    for (int i = 0; i < chatUsers.size(); i++) {
                        if (chatUsers.get(i).equals(user1.getId())) {
                            userList.add(user1);

                        }
                    }

                }

                // redirect to messages activity "ChatActivity"
                adapter = new BrowseUserAdapter(usersChats.this, userList, new CustomItemClickListener() {
                    @Override
                    public void OnItemClick(View v, int pos) {

                        user n = userList.get(pos);
                        String s = n.getId();
                        String o = fuserID;


                        Intent in = new Intent(usersChats.this, chatActivity.class);
                        in.putExtra(sender, "" + s);
                        in.putExtra(receiver, s);

                        startActivity(in);
                    } // end on item click listener
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//


    }
    @Override
    public void onBackPressed(){
        if(drawerLayout66.isDrawerOpen(GravityCompat.START)){

            drawerLayout66.closeDrawer(GravityCompat.START);
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
            welcome.setText("Welcome");
        }
//        if(user == null){
//            getMenuInflater().inflate(R.menu.visitor_menu, menu);}

        // else{
        // getMenuInflater().inflate(R.menu.menudrawer, menu);}
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1 = new Intent(usersChats.this, StudentActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent(usersChats.this, Profile.class);
                startActivity(intent2);
                break;
            case R.id.nav_chat:
                Intent intent3 = new Intent(usersChats.this, usersChats.class);
                startActivity(intent3);
                break;

            case R.id.nav_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(usersChats.this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    // signOut
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(usersChats.this, MainActivity.class));
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
                Intent intent4 = new Intent(usersChats.this, MainActivity.class);
                startActivity(intent4);
        }
        return true;
    }

}