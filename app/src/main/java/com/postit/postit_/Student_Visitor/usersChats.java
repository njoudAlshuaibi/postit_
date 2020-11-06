package com.postit.postit_.Student_Visitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.postit.postit_.Adapter.BrowseUserAdapter;
import com.postit.postit_.R;

public class usersChats extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BrowseUserAdapter adapter;
    private List<user> mUser;
    FirebaseUser fuser;
    DatabaseReference Ref;
    private List<String> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_chats);


        recyclerView = findViewById(R.id.usersChatsRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mUser = new ArrayList<>();
        adapter = new BrowseUserAdapter(getApplicationContext(), mUser);
        recyclerView.setAdapter(adapter);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        String fuserID=fuser.getUid();
        userList = new ArrayList<>();
        Ref = FirebaseDatabase.getInstance().getReference("Massages");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chat cht = dataSnapshot.getValue(chat.class);
                    if (cht.getSenderId().equals(fuserID)) {
                        userList.add(cht.getReceiverId());
                    }
                    if (cht.getReceiverId().equals(fuser.getUid())) {
                        userList.add(cht.getSenderId());
                    }
                }
                readChats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }//

    private void readChats() {

        Ref = FirebaseDatabase.getInstance().getReference("users");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot09) {
                mUser.clear();
                for (DataSnapshot dataSnapshot9 : snapshot09.getChildren()) {
                    user user1 = dataSnapshot9.getValue(user.class);
                    for (String id : userList) {
                        if (user1.getId().equals(id)) {
                            if (mUser.size() != 0) {
                                for (user user11 : mUser) {
                                    if (!user1.getId().equals(user11.getId())) {
                                        mUser.add(user1);
                                    }
                                }
                            } else {
                                mUser.add(user1);
                            }
                        }
                    }
                }//
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}