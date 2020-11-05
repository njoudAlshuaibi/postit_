package com.postit.postit_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.postit.postit_.Objects.user;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.postit.postit_.Adapter.BrowseUserAdapter;

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




    }

}