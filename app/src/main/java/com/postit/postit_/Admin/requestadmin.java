package com.postit.postit_.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.requests;
import com.postit.postit_.R;

import java.util.ArrayList;

public class requestadmin extends AppCompatActivity  {
    private ListView requestsListView;
    private FirebaseDatabase database;
    private DatabaseReference requestsRef;
    private DatabaseReference ref;
    private Button requestbutton;
    public static final String maj = "com.postit.postit_.maj";
    public static final String cou = "com.postit.postit_.cou";
    public static final String ch = "com.postit.postit_.ch";
    private TextView noReq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestadmin);
        Toolbar toolbar = findViewById(R.id.toolbar_request);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Requests");
        toolbar.setTitleTextColor(0xFF000000);

//        requestbutton = findViewById(R.id.requestbutton);
        final ArrayList<requests> requestsList = new ArrayList<>();
        final MyCustomAdapter myadpter = new MyCustomAdapter(requestsList);
        noReq= (TextView) findViewById(R.id.noReq);

        requestsListView = findViewById(R.id.viewReq);
        requestsListView.setAdapter(myadpter);
        ref = FirebaseDatabase.getInstance().getReference();
        requestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        requestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotr) {
                if(!snapshotr.exists()){
                    noReq.setText("There is no request");
                }
                requestsList.clear();
                for (DataSnapshot snapshotx : snapshotr.getChildren()) {
                    requests reqObj = snapshotx.getValue(requests.class);
//                   String reqdisplay = "Major: " + reqObj.getRequestedMajor() + "\nCourse: " + reqObj.getRequestedCourse() + "\nChapter/s: " + reqObj.getRequestedChapter();
                    requestsList.add(reqObj);
                }
                myadpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requestadmin.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                // signOut
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(requestadmin.this, MainActivity.class));

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

        } else if (id == R.id.home) {
            startActivity(new Intent(requestadmin.this, mainAdmin.class));
        }
        return true;
    }



    class MyCustomAdapter extends BaseAdapter {
        ArrayList<requests> Items = new ArrayList<requests>();

        MyCustomAdapter(ArrayList<requests> Items) {
            this.Items = Items;

        }


        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public String getItem(int position) {
            return Items.get(position).getId();

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.request_view, null);

            TextView txtname = (TextView) view1.findViewById(R.id.txt_name);
            txtname.setText("Major: " + Items.get(i).getRequestedMajor() + "\nCourse: " + Items.get(i).getRequestedCourse() + "\nChapter/s: " +  Items.get(i).getRequestedChapter());
            ImageButton requestbutn = (ImageButton) view1.findViewById(R.id.requestbutn);
            ImageButton deleterequest = (ImageButton) view1.findViewById(R.id.deleterequest);
            requestbutn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(requestadmin.this, AdminActivity.class);
                    intent.putExtra(maj, Items.get(i).getRequestedMajor());
                    intent.putExtra(cou, Items.get(i).getRequestedCourse());
                    intent.putExtra(ch, Items.get(i).getRequestedChapter());
                    startActivity(intent);
                }
            });
            deleterequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
               final String m = Items.get(i).getRequestedMajor();
               final String c = Items.get(i).getRequestedCourse();
               final String ch = Items.get(i).getRequestedChapter();


                        new AlertDialog.Builder(requestadmin.this)
                                .setIcon(android.R.drawable.ic_delete)
                                .setTitle("Are you sure?")
                                .setMessage("Do you want to delete this request?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestsRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot child : snapshot.getChildren()) {
                                                    requests findreq = child.getValue(requests.class);
                                                    String maj = findreq.getRequestedMajor();
                                                    String co = findreq.getRequestedCourse();
                                                    String ch1 = findreq.getRequestedChapter();


                                                    if (maj.equals(m) && co.equals(c) && ch1.equals(ch)) {
                                                        String deletedreq = findreq.getId();
                                                    deleteChapter(deletedreq);

                                                    break;
                                                   }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();


                    }

            });
            return view1;

        }
        public void deleteChapter(String chapterKey) {
            requestsRef.child(chapterKey.trim()).removeValue();
        }
    }
    }