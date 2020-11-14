package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.postit.postit_.Admin.mainAdmin;
import com.postit.postit_.Admin.requestadmin;
import com.postit.postit_.Student_Visitor.CreateAccountActivity;
import com.postit.postit_.Student_Visitor.StudentActivity;
import com.postit.postit_.Student_Visitor.chatActivity;
import com.postit.postit_.Student_Visitor.forgetPasswordActivity;
import com.postit.postit_.Student_Visitor.mynotes;
import com.postit.postit_.Student_Visitor.usersChats;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
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
            if (desc.equals("You have a new comment"))
            {

                // The following can be used to open an Activity of your choice.
                // Replace - getApplicationContext() - with any Android Context.

                Intent intent = new Intent(getApplicationContext(), mynotes.class);
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
    public static String LoggedIn_User_Email;
    private TextView createAccount;//test
    private TextView forgetPassword;
    private TextView visitor;
    //juju start
    private Button login;
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //juju end
//n
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");

// Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        //juju start
        mAuth = FirebaseAuth.getInstance();
        // juju end

        createAccount = (TextView) findViewById(R.id.createAccount);
        forgetPassword  = (TextView) findViewById(R.id.forgetPassword);
        visitor  = (TextView) findViewById(R.id.visitor);
        //juju start
        login = (Button) findViewById(R.id.loginButton);
        editTextEmail = (EditText) findViewById(R.id.loginEmail);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);
        //juju end

        createAccount.setOnClickListener(new View.OnClickListener() {



            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
            }//End onClick()
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, forgetPasswordActivity.class));
            }
        });
        visitor.setOnClickListener(new View.OnClickListener() {



            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentActivity.class));
            }//End onClick()
        });
        // juju start
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });
        // juju end

    }

    // juju start
    private void login(){
        final String email = editTextEmail.getText().toString().trim();
        LoggedIn_User_Email = email;
        OneSignal.sendTag("User_ID",LoggedIn_User_Email);
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 8){
            editTextPassword.setError("Min password length should be 8 characters");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser().getEmail().equalsIgnoreCase("swe444@gmail.com"))
                        startActivity(new Intent(MainActivity.this, mainAdmin.class));
                    else {
                        startActivity(new Intent(MainActivity.this, StudentActivity.class));
                    }
                }else{
                    Toast.makeText(MainActivity.this, "failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    ///////juju end j
//vv
}