package com.postit.postit_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

public class popupwindowchapters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindowchapters);

        DisplayMetrics ja = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ja);



        int width = ja.widthPixels;
        int height = ja.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        Intent intent = getIntent();
        final String MajorN = intent.getStringExtra(BrowseNotes.EXTRA_TEXT7);
        final String CourseN = intent.getStringExtra(BrowseNotes.EXTRA_TEXT8);
        final String ChapterN = intent.getStringExtra(BrowseNotes.EXTRA_TEXT9);




    }

}
