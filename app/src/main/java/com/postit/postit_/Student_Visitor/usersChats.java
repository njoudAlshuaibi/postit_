package com.postit.postit_.Student_Visitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Objects.user;
import com.postit.postit_.Objects.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.postit.postit_.Adapter.BrowseUserAdapter;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;

public class usersChats extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BrowseUserAdapter adapter;
    FirebaseUser fuser;
    DatabaseReference Ref, usersRef;
    private List<user> userList = new ArrayList<>();
    Toolbar toolbar;
    String fuserID;
    String recevierEQLcurrent;
    public static final String sender = "com.postit.postit_.sender";
    public static final String receiver = "com.postit.postit_.receiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_chats);

        toolbar = findViewById(R.id.toolbar99);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Direct");
        toolbar.setTitleTextColor(0xFFB8B8B8);
        recyclerView = findViewById(R.id.usersChatsRV);


//////////////////////////////////////
        Ref = FirebaseDatabase.getInstance().getReference("Massages");
        Ref.keepSynced(true);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        fuserID = fuser.getUid();



        // find users who start chat

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotiooo) {
                userList.clear();
//                adapter.notifyDataSetChanged();
                for (DataSnapshot messageSnapshotii : snapshotiooo.getChildren()) {
                    chat chatObj = messageSnapshotii.getValue(chat.class);
                    if (chatObj.getReceiverId().equals(fuserID)) {
                        recevierEQLcurrent = chatObj.getSenderId();

                    }
                }
                //find user object to add it to the list while knowing its ID
                usersRef = FirebaseDatabase.getInstance().getReference("users");
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshotp : snapshot.getChildren()) {
                            user user1 = dataSnapshotp.getValue(user.class);
                            if (recevierEQLcurrent.equals(user1.getId()))
                                userList.add(user1);

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
                                in.putExtra(receiver,o);

                                startActivity(in);
                            } // end on item click listener
                        });
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                        adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}