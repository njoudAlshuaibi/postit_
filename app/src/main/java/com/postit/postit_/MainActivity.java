package com.postit.postit_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//raya,,,
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView createAccount;//test
    private TextView forgetPassword;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView logIn;

    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");


        createAccount = (TextView) findViewById(R.id.createAccount);
        createAccount.setOnClickListener(this);

        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(this);

        logIn = (TextView) findViewById(R.id.loginButton);
        logIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.loginEmail);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);

        //createAccount.setOnClickListener(new View.OnClickListener() {

        //  public void onClick(View view) {
        //     startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
        //   }//End onClick()
        // });


        //forgetPassword.setOnClickListener(new View.OnClickListener() {
        //   @Override
        // public void onClick(View view) {
        //     startActivity(new Intent(MainActivity.this, forgetPasswordActivity.class));
        // }
        // });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccount:
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
                break;
            case R.id.forgetPassword:
                startActivity(new Intent(MainActivity.this, forgetPasswordActivity.class));
                break;
            case R.id.loginButton:
                logIn();
                break;
        }

    }

    private void logIn(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is requied");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is requied");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            editTextPassword.setError("Password minimum length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // redirect to profile page
                    startActivity(new Intent(MainActivity.this, ProfileTest2.class));

                } else {
                    Toast.makeText(MainActivity.this, "Faild to login!", Toast.LENGTH_LONG).show();
                }
            }
        } );
    }
}






















