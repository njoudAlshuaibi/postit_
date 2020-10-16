package com.postit.postit_.Student_Visitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.postit.postit_.R;

public class previewnote extends AppCompatActivity {
    TextView notepre;

    TextView notepr2;
    TextView notetit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewnote);
        notepre= findViewById(R.id.notepre);
        notepr2= findViewById(R.id.notepr2);
        notetit= findViewById(R.id.notetit);


        DisplayMetrics aa = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(aa);

//
//        int width = aa.widthPixels;
//        int height = aa.heightPixels;
//        getWindow().setLayout((int) (width * .8), (int) (height * .7));

        Intent intent = getIntent();

        final String title = intent.getStringExtra(ExplorerNote.EXTRA_TEXT);
        final String caption = intent.getStringExtra(ExplorerNote.EXTRA_TEXT2);
        final String email = intent.getStringExtra(ExplorerNote.EXTRA_TEXT3);


        notetit.setText(title);
        notepre.setText("\n\n\n caption :\n\n"+caption);
        notepr2.setText( "\n\n\n\n\n\n\n\n\n\n written by : "+ email);

//vvb
        //,,,
    }
}