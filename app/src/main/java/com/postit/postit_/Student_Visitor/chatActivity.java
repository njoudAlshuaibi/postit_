package com.postit.postit_.Student_Visitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Adapter.BrowseChatAdapter;
import com.postit.postit_.Objects.chat;

import com.postit.postit_.Objects.user;
import com.postit.postit_.R;

public class chatActivity extends AppCompatActivity {

    private DatabaseReference massagesRef, usersRef;
    private List<chat> chatList = new ArrayList();
    private RecyclerView recyclerView;
    private BrowseChatAdapter adapter;
    private EditText edtPost;
    private ImageView imgSend;
    private FirebaseUser fuser;
    private String receiverUserId, receiverEmail;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        Intent intent1 = getIntent();
        receiverEmail = intent1.getStringExtra(previewnote.writerEmail);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot jj : snapshot.getChildren()) {
                    user u = jj.getValue(user.class);
                    if (u.getEmail().equals(receiverEmail)) {
                        receiverUserId = jj.getKey();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        massagesRef = FirebaseDatabase.getInstance().getReference().child("Massages");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        adapter = new BrowseChatAdapter(this, chatList);
        readMessages(fuser.getUid(), receiverUserId);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        edtPost = findViewById(R.id.edtPost);
        imgSend = findViewById(R.id.imgSend);

//        intent = getIntent();
//        final String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = edtPost.getText().toString();
                if (!msg.equals("")) {

                    sendMessage(fuser.getUid(), receiverUserId, msg);
                } else {
                    Toast.makeText(chatActivity.this, "You can't send empty message ", Toast.LENGTH_SHORT).show();
                }
                edtPost.setText("");


            }//onClick
        });


    }

    private void sendMessage(String sender, String receiver, String message) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String id = massagesRef.push().getKey();
        Date date = new Date();
        hashMap.put("senderId", sender);
        hashMap.put("receiverId", receiver);
        hashMap.put("msg", message);
        // hashMap.put("id", message);
        //hashMap.put("msgTime", date.getTime());
        chat f = new chat(message, sender, receiver);
        massagesRef.child(id).setValue(hashMap);
//test
    }

    private void readMessages(final String myid, final String receiverUserId) {

        massagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                adapter.notifyDataSetChanged();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    chat cht = snapshot1.getValue(chat.class);
                    if (cht.getReceiverId().equals(myid) && cht.getSenderId().equals(receiverUserId) ||
                            cht.getReceiverId().equals(receiverUserId) && cht.getSenderId().equals(myid)) {
                        chatList.add(cht);
                    }

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}