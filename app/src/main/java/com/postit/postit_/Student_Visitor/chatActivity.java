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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Bundle;

import com.postit.postit_.Adapter.BrowseChatAdapter;
import com.postit.postit_.Objects.chat;

import com.postit.postit_.R;

public class chatActivity extends AppCompatActivity {

    private DatabaseReference massagesRef;
    private List<chat> chatList = new ArrayList();
    private RecyclerView recyclerView;
    private BrowseChatAdapter adapter;
    private EditText edtPost;
    private ImageView imgSend;
    private String receiverUserId;

    private static final String TAG = chatActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        receiverUserId= intent.getStringExtra(previewnote.noteWriterIDtoChatActivity);

    }
    private void init() {
        massagesRef = FirebaseDatabase.getInstance().getReference().child("Massages");
        setListener();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BrowseChatAdapter(this, chatList);
        recyclerView.setAdapter(adapter);


        edtPost = findViewById(R.id.edtPost);
        imgSend = findViewById(R.id.imgSend);
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (edtPost.getText() != null && !edtPost.getText().toString().equals("")&& !edtPost.getText().toString().trim().equals("")){

                    String key = massagesRef.push().getKey();
                    Date date = new Date();
                    massagesRef.child(key).setValue(new chat(key, edtPost.getText().toString(), FirebaseAuth.getInstance().getUid(), receiverUserId, date.getTime()));
                    // Log.d("CHATACT", "after creating messege" );
                    edtPost.setText("");

                }


            }//onClick
        });


    }//end init


    private void setListener() {

        massagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                chat chat = dataSnapshot.getValue(chat.class);

                if(chat.getReceiverId().equals( receiverUserId)
                        && chat.getSenderId().equals(FirebaseAuth.getInstance().getUid()))
                    chatList.add(chat);
                else if(chat.getSenderId().equals(receiverUserId)
                        && chat.getReceiverId().equals(FirebaseAuth.getInstance().getUid()))
                    chatList.add(chat);

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatList.size()-1);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Log.i(TAG, "onChildChanged");
                chat chat = dataSnapshot.getValue(chat.class);

                for (int i = 0; i < chatList.size(); i++) {
                    if (chatList.get(i).getId().equals(chat.getId())) {
                        chatList.set(i, chat);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("onChildRemoved", "onChildRemoved");

                chat chat = dataSnapshot.getValue(chat.class);

                for (int i = 0; i < chatList.size(); i++) {
                    if (chatList.get(i).getId().equals(chat.getId())) {
                        chatList.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("onChildMoved", "onChildMoved");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("onCancelled", "onCancelled");

            }
        });
    }//end setListener

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}