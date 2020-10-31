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
    private FirebaseUser fuser;
    Intent intent ;

    private static final String TAG = chatActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // massagesRef = FirebaseDatabase.getInstance().getReference().child("Massages");
        setListener();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BrowseChatAdapter(this, chatList);
        recyclerView.setAdapter(adapter);


        edtPost = findViewById(R.id.edtPost);
        imgSend = findViewById(R.id.imgSend);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = edtPost.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fuser.getUid() , userid , msg);
                }else {
                    Toast.makeText(chatActivity.this , "You can't send empty message ", Toast.LENGTH_SHORT).show();
                }
                edtPost.setText("");

//                if (edtPost.getText() != null && !edtPost.getText().toString().equals("")&& !edtPost.getText().toString().trim().equals("")){
//
//                    String key = massagesRef.push().getKey();
//                    Date date = new Date();
//                    massagesRef.child(key).setValue(new chat(key, edtPost.getText().toString(), FirebaseAuth.getInstance().getUid(), sharedPreference.getString(getApplicationContext(), Constants.keys.CLICKED_PARENT,"error"), date.getTime()));
//                    // Log.d("CHATACT", "after creating messege" );
//                    edtPost.setText("");
//
//                }


            }//onClick
        });


    }
    private void init() {



    }//end init


    private void setListener() {

//        massagesRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                chat chat = dataSnapshot.getValue(chat.class);
//
//                if(chat.getReceiverId().equals( sharedPreference.getString(getApplicationContext(), Constants.keys.CLICKED_PARENT,"error"))
//                        && chat.getSenderId().equals(FirebaseAuth.getInstance().getUid()))
//                    chatList.add(chat);
//                else if(chat.getSenderId().equals(sharedPreference.getString(getApplicationContext(), Constants.keys.CLICKED_PARENT,"error"))
//                        && chat.getReceiverId().equals(FirebaseAuth.getInstance().getUid()))
//                    chatList.add(chat);
//
//                if (adapter != null) {
//                    adapter.notifyDataSetChanged();
//                    recyclerView.scrollToPosition(chatList.size()-1);
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                Log.i(TAG, "onChildChanged");
//                chat chat = dataSnapshot.getValue(chat.class);
//
//                for (int i = 0; i < chatList.size(); i++) {
//                    if (chatList.get(i).getId().equals(chat.getId())) {
//                        chatList.set(i, chat);
//                        adapter.notifyDataSetChanged();
//                        break;
//                    }
//                }
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                Log.i("onChildRemoved", "onChildRemoved");
//
//                chat chat = dataSnapshot.getValue(chat.class);
//
//                for (int i = 0; i < chatList.size(); i++) {
//                    if (chatList.get(i).getId().equals(chat.getId())) {
//                        chatList.remove(i);
//                        adapter.notifyDataSetChanged();
//                        break;
//                    }
//                }
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.i("onChildMoved", "onChildMoved");
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.i("onCancelled", "onCancelled");
//
//            }
//        });
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

    private void sendMessage(String sender , String receiver , String message){
        massagesRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        massagesRef.child("Massages").push().setValue(hashMap);


    }

}