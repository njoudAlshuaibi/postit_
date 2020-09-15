package com.postit.postit_;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private EditText editTextuserName, editTextsignupEmail, editTextsignupPass, editTextsignupcPass;
    private String college, major;
    Spinner spinner, spinner2;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        registerButton= findViewById(R.id.signupBtn);
        /* teset it later */
        registerButton.setOnClickListener((View.OnClickListener) this);
        editTextuserName =(EditText) findViewById((R.id.userName));
        editTextsignupEmail =(EditText) findViewById((R.id.signupEmail));
        editTextsignupPass= (EditText) findViewById((R.id.signupPass));
        editTextsignupcPass =(EditText) findViewById((R.id.signupcPass));


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
        String suserName= editTextuserName.getText().toString().trim();
        String semail= editTextsignupEmail.getText().toString().trim();
        String ssignupPass= editTextsignupPass.getText().toString().trim();
        String ssignupcPass= editTextsignupcPass.getText().toString().trim();
        String scollege= college.trim();
        String smajor=major.trim();

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
        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            editTextsignupEmail.setError("Please provide valid email");
            editTextsignupEmail.requestFocus();
            return;
        }



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