package com.postit.postit_.Student_Visitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Adapter.BrowseChatAdapter;
import com.postit.postit_.Admin.AdminActivity;
import com.postit.postit_.Admin.mainAdmin;
import com.postit.postit_.Objects.chat;

import com.postit.postit_.Objects.user;
import com.postit.postit_.R;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;


public class chatActivity extends AppCompatActivity {

    private DatabaseReference massagesRef, usersRef;
    private List<chat> chatList ;
    private RecyclerView recyclerView;
    private BrowseChatAdapter adapter;
    private EditText edtPost;
    private ImageView imgSend;
    private FirebaseUser fuser;
    private String receiverUserId,rname, receiverEmail;
    Intent intent;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar=findViewById(R.id.toolbar9988);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitleTextColor(0xFFB8B8B8);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        /////



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
        if(receiverUserId==null) {
            Intent intent = getIntent();
            receiverUserId = intent.getStringExtra(usersChats.receiver);

//        Intent intent2 = getIntent();

//        receiverUserId = intent2.getStringExtra(previewnote.receiver);
        }

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot jj : dataSnapshot.getChildren()) {
                    user u = jj.getValue(user.class);
                    if (receiverUserId.equals(jj.getKey())) {
                        rname = u.getUsername();
                        receiverEmail = u.getEmail();
                        getSupportActionBar().setTitle(rname);

                    }

                }






            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        massagesRef = FirebaseDatabase.getInstance().getReference().child("Massages");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        String fuserid = fuser.getUid();
        massagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readMessages(fuserid, receiverUserId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
       // adapter = new BrowseChatAdapter(this, chatList);


        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setAdapter(adapter);


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


//Notification
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID",LoggedIn_User_Email);
        //End notify




    }

    private void sendMessage(String sender, String receiver, String message) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String id = massagesRef.push().getKey();
        Date date = new Date();
        hashMap.put("senderId", sender);
        hashMap.put("receiverId", receiver);
        hashMap.put("msg", message);
        chat f = new chat(message, sender, receiver);
        massagesRef.child(id).setValue(hashMap);

        sendNotification("You Have Received A Message" , receiverEmail);


    }

    private void readMessages(final String myid, final String receiverUserId) {

        chatList = new ArrayList();
        massagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
             //   adapter.notifyDataSetChanged();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    chat cht = snapshot1.getValue(chat.class);
                    if (cht.getReceiverId().equals(myid) && cht.getSenderId().equals(receiverUserId) ||
                            cht.getReceiverId().equals(receiverUserId) && cht.getSenderId().equals(myid)) {
                        chatList.add(cht);
                    }
                    adapter = new BrowseChatAdapter(chatActivity.this , chatList );
                    recyclerView.setAdapter(adapter);
                }

              //  adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Back) {
            startActivity(new Intent(chatActivity.this, usersChats.class));

        }
        return true;
    }

    private void sendNotification(final String message, final String senderMail)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    OneSignal.sendTag("User_ID",FirebaseAuth.getInstance().getCurrentUser().getEmail());//sender email

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic NWYxMWRhMjMtNmQyYy00ZWQyLWI0ODQtYjk3Mjg5ODYzYjNl");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"23583377-2cda-44fa-ae0d-604256300b33\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + senderMail + "\"}],"
                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \""+message+"\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        NotificationManagerCompat.from(getApplicationContext()).cancelAll();

    }

}