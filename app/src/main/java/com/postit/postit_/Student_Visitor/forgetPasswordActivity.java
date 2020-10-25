package com.postit.postit_.Student_Visitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.postit.postit_.MainActivity;
import com.postit.postit_.R;

public class forgetPasswordActivity extends AppCompatActivity {

    private TextView cancel;
    private EditText emailEditText;
    private Button resetpasswordButton;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        cancel= (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(forgetPasswordActivity.this, MainActivity.class));
            }//End onClick()
        });


        emailEditText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        resetpasswordButton =(Button) findViewById(R.id.ResetButton);

        auth= FirebaseAuth.getInstance();

        resetpasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });
    }
    private void resetpassword(){
        String ssemail= emailEditText.getText().toString().trim();

        if(ssemail.isEmpty()){
            emailEditText.setError("Enter a valid email");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(ssemail).matches()){
            emailEditText.setError("Please provide valid email");
            emailEditText.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(ssemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgetPasswordActivity.this, "check your email to reset your password",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(forgetPasswordActivity.this,"Try again! this email not registered ",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}