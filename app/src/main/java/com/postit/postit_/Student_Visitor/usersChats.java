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

    public static final String sender = "com.postit.postit_.sender";
    public static final String receiver = "com.postit.postit_.receiver";
    FirebaseUser fuser;
    DatabaseReference Ref, usersRef;
    Toolbar toolbar;
    String fuserID;
    String recevierEQLcurrent = "";
    boolean f = false;
    boolean ifAdded = false;
    private RecyclerView recyclerView;
    private BrowseUserAdapter adapter;
    private List<user> userList = new ArrayList<>();
    private List<String> chatUsers = new ArrayList<>();

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
                chatUsers.clear();
//                adapter.notifyDataSetChanged();
                for (DataSnapshot messageSnapshotii : snapshotiooo.getChildren()) {
                    ifAdded=false;
                    chat chatObj = messageSnapshotii.getValue(chat.class);
                    if (chatObj.getReceiverId().equals(fuserID)) {

//                        recevierEQLcurrent = chatObj.getSenderId();
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
                       // recevierEQLcurrent = chatObj.getReceiverId();

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
                userList.clear();
                for (DataSnapshot dataSnapshotp : snapshot.getChildren()) {
                    user user1 = dataSnapshotp.getValue(user.class);
                    for (int i = 0; i < chatUsers.size(); i++) {
                        if (chatUsers.get(i).equals(user1.getId())) {
                            userList.add(user1);

//                            Ref.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    for (DataSnapshot messageSnapshotiit : snapshot.getChildren()) {
//                                        chat chatObj1 = messageSnapshotiit.getValue(chat.class);
//
//                                        if ((chatObj1.getSenderId().equals(user1.getId())) && (chatObj1.getReceiverId().equals(fuser.getUid()))) {
//                                            for (int i = 0; i < userList.size(); i++) {
//                                                if (userList.get(i).getId().equals(chatObj1.getSenderId())) {
//                                                    f = true;
//                                                    break;
//                                                }
//                                            }
//                                            if (f == false)
//                                                userList.add(user1);
//                                        }
////                                                || (chatObj1.getSenderId().equals(user1.getId())))
////                                            if( if(chatObj1.getReceiverId().equals(fuser.getUid()))|| (chatObj1.getSenderId().equals(fuser.getUid())))
////                                            if (f == false)
//
//
////                                                if( (chatObj1.getReceiverId().equals(fuser.getUid()))|| (chatObj1.getSenderId().equals(fuser.getUid())))
////                                                    if( (user1.getId().equals(fuser.getUid())|| (user1.getId().equals(fuser.getUid()))))
//
//                                    }
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
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


}