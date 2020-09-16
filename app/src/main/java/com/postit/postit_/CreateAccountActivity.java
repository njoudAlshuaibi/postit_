package com.postit.postit_;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextuserName, editTextsignupEmail, editTextsignupPass, editTextsignupcPass;
    private String college, major;
    Spinner spinner, spinner2;
    private TextView registerButton;
    private TextView haveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        registerButton= findViewById(R.id.signupBtn);
        /* teset it later */
        registerButton.setOnClickListener(this);
        editTextuserName =(EditText) findViewById((R.id.userName));
        editTextsignupEmail =(EditText) findViewById((R.id.signupEmail));
        editTextsignupPass= (EditText) findViewById((R.id.signupPass));
        editTextsignupcPass =(EditText) findViewById((R.id.signupcPass));
//switch between sign up and sign in
        haveAccount=findViewById(R.id.haveAccount);
        haveAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
            }//End onClick()
        });
        // save the selected college to a string
        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Collage,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                college= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // save the selected major to a string
        spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Major,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                major= parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.signupBtn:
                registration();
                break;

        }
    }
    //creat an account and validate it
    private void registration() {
        final String suserName= editTextuserName.getText().toString().trim();
        final String semail= editTextsignupEmail.getText().toString().trim();
        final String ssignupPass= editTextsignupPass.getText().toString().trim();
        String ssignupcPass= editTextsignupcPass.getText().toString().trim();
        final String scollege= college.trim();
        final String smajor=major.trim();

        if(suserName.isEmpty()){
            editTextuserName.setError("User name is required");
            editTextuserName.requestFocus();
            return;
        }
        if(semail.isEmpty()){
            editTextsignupEmail.setError("Email is required");
            editTextsignupEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            editTextsignupEmail.setError("Please provide valid email");
            editTextsignupEmail.requestFocus();
            return;
        }
        if(ssignupPass.isEmpty()){
            editTextsignupPass.setError("Password is required");
            editTextsignupPass.requestFocus();
            return;
        }
        if(ssignupPass.length()<6){
            editTextsignupPass.setError("Min password length should be 6 characters!");
            editTextsignupPass.requestFocus();
            return;
        }
        if(ssignupcPass.isEmpty()){
            editTextsignupcPass.setError("Confirm your Password");
            editTextsignupcPass.requestFocus();
            return;
        }
        if(!ssignupcPass.equals(ssignupPass)){
            editTextsignupcPass.setError("not matching password");
            editTextsignupcPass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(semail,ssignupPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                               if(task.isSuccessful()){
                                                   user USER = new user(suserName,semail,ssignupPass,scollege,smajor);
                                                   FirebaseDatabase.getInstance().getReference("users")
                                                           .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                           .setValue(USER).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if (task.isSuccessful()) {
                                                               Toast.makeText(CreateAccountActivity.this, "User registered Successfully ", Toast.LENGTH_LONG).show();
                                                               startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));

                                                           }
                                                           else {
                                                               Toast.makeText(CreateAccountActivity.this, "Registration failed ", Toast.LENGTH_LONG).show();

                                                           }
                                                       }
                                                   }  );
                                               }
                                               else {
                                                   Toast.makeText(CreateAccountActivity.this, "this email is Already taken", Toast.LENGTH_LONG).show();

                                               }

                                           }
                                       }
                );




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




}


