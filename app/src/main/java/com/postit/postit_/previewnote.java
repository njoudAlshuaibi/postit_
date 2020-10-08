package com.postit.postit_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class previewnote extends AppCompatActivity {
TextView notepre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewnote);
        notepre= findViewById(R.id.notepre);

        DisplayMetrics aa = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(aa);

        int width = aa.widthPixels;
        int height = aa.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .5));

        Intent intent = getIntent();

        final String id = intent.getStringExtra(BrowseNotes.EXTRA_TEXT);
        final String a = intent.getStringExtra(BrowseNotes.EXTRA_TEXT2);

        notepre.setText(id+"   caption : "+a);

    }
}