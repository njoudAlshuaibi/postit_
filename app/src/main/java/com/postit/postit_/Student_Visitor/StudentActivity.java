package com.postit.postit_.Student_Visitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.postit.postit_.Admin.requestadmin;
import com.postit.postit_.MainActivity;
import com.postit.postit_.Objects.user;
import com.postit.postit_.R;
import com.postit.postit_.helper.Session;

import org.json.JSONObject;


public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String customKey;

            Log.i("OSNotificationPayload", "result.notification.payload.toJSONObject().toString(): " + result.notification.payload.toJSONObject().toString());


            if (data != null) {
                customKey = data.optString("customkey", null);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

            String desc=result.notification.payload.body;
            Log.d("123456" , "Body ");

            if (desc.equals("There is a new request"))
            {

                // The following can be used to open an Activity of your choice.
                // Replace - getApplicationContext() - with any Android Context.

                Intent intent = new Intent(getApplicationContext(), requestadmin.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }

            if (desc.equals("You Have Received A Message"))
            {

                // The following can be used to open an Activity of your choice.
                // Replace - getApplicationContext() - with any Android Context.

                Intent intent = new Intent(getApplicationContext(), usersChats.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }


            // The following can be used to open an Activity of your choice.
            // Replace - getApplicationContext() - with any Android Context.

//      Intent intent = new Intent(getApplicationContext(), requestadmin.class);
//
// intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//
// startActivity(intent);

            // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
            //   if you are calling startActivity above.
     /*
        <application ...>
          <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
        </application>
     */
        }
    }



    DrawerLayout drawerLayout;
NavigationView navigationView;
Toolbar toolbar;
    Session session;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
   private TextView welcome;
    private DatabaseReference g =  FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        //


        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
//
         setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("HOME");
        toolbar.setTitleTextColor(0xFF000000);
        ///
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        session = new Session(getApplicationContext());
        Menu menu= navigationView.getMenu();
        if(user==null){
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_chat).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
        }
        else{
            menu.findItem(R.id.nav_login).setVisible(false);
        }
        CardView cv = (CardView) findViewById(R.id.Request);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null)
                {
                    new AlertDialog.Builder(StudentActivity.this)
                            .setTitle("you need to login first")
                            .setMessage("Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent (StudentActivity.this, MainActivity.class));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();                }
                else
                {
                    startActivity(new Intent (StudentActivity.this, Pop.class));
                }


            }
        });

        CardView ra = (CardView) findViewById(R.id.FavoriteList);
        ra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null)
                {
                    new AlertDialog.Builder(StudentActivity.this)
                            .setTitle("you need to login first")
                            .setMessage("Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent (StudentActivity.this,MainActivity.class));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();                }
                else
                {
                    startActivity(new Intent (StudentActivity.this, favoritelist.class));
                }

            }
        });

        CardView nj = (CardView) findViewById(R.id.Mynote);
        nj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null)
                {
                    new AlertDialog.Builder(StudentActivity.this)
                            .setTitle("you need to login first")
                            .setMessage("Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent (StudentActivity.this,MainActivity.class));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();                }
                else
                {
                    startActivity(new Intent (StudentActivity.this, mynotes.class));
                }

            }
        });

        CardView ma = (CardView) findViewById(R.id.BrowseNotes);
        ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpopUpWindow();
            }
        });



    }
@Override
public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

        drawerLayout.closeDrawer(GravityCompat.START);
    }
    else{
        super.onBackPressed();
    }
}

    private void openpopUpWindow() {
        Intent popupwindow2 = new Intent(StudentActivity.this, popUpWindow.class);
        startActivity(popupwindow2);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item ) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1= new Intent(StudentActivity.this,StudentActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2= new Intent(StudentActivity.this,Profile.class);
                startActivity(intent2);
                break;
            case R.id.nav_chat:
                Intent intent3= new Intent(StudentActivity.this,usersChats.class);
                startActivity(intent3);
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);

                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                    // signOut
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(StudentActivity.this, MainActivity.class));

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
                Intent intent4= new Intent(StudentActivity.this,MainActivity.class);
                startActivity(intent4);

         }
        return true;}

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManagerCompat.from(getApplicationContext()).cancelAll();

    }
}