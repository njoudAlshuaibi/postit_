package com.postit.postit_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class forgetPasswordActivity extends AppCompatActivity {

    private TextView cancel;
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
    }
}